<?php
require_once 'builder.class.php';

class basedata extends builder {

	private $table;
	private $fields;
	private $tableinfo;
	private $className = '';
	private $questionMarks = '';
	private $insert_cols = '';

	function basedata($table = '',$fields, $tableinfo='') {
		$this->table = $table;
		$this->fields = $fields;
		$this->tableinfo = $tableinfo;
		$this->className = $this->get_classname_from_table($table);
		for ($i = 0; $i < count($this->fields) - 1; $i++) {
			$this->questionMarks .= "?,";
		}
		$this->questionMarks = removeLastChar($this->questionMarks);
		for ($i = 1; $i < count($this->fields); $i++) {
			$this->insert_cols .= $this->fields[$i] . ",";
		}
		$this->insert_cols = removeLastChar($this->insert_cols);
	}

	public function _build() {
?>
<%
abstract class Base<?=$this->className?>Model extends BaseModel{
<?
$field_string = "";
foreach ($this->fields as $field) {
	$field_string = $field_string. "$" . $field . ",";
	?>
	var $<?=$field?>;
<?}
$field_string = substr($field_string, 0,-1);
?>
	var $conn;
	var $validator;

    public function __construct() {
    	global $conn;
        $this->conn = $conn;
        $this->validator = new Validator();
    }

	public function init($arr = "") {
		if (is_array($arr)) {
			$this->initArray($arr);
		} else {
			$this->initRequest();
		}	
	}

    private function initArray($daoArray) {
<?foreach ($this->fields as $field) {?>
		$this-><?=$field?> = $daoArray["<?=$field?>"];
<?}?>
    }
    
    private function initRequest() {
<?foreach ($this->fields as $field) {?>
		$this-><?=$field?> = getParameter("<?=$field?>");
<?}?>
    }
<?

		#---------------------INSERT METHOD -------------------------------------
?>
	// since insert is still there...
	public function create() {
		return $this->insert();
	}

    public function insert() {
		$valErrs = $this->validate();
		if ($valErrs != 0) {
			return $valErrs;
		}
        $sql = "insert into <?=$this->table?> (<?=$this->insert_cols?>) values(<?=$this->questionMarks?>)";
		$ps = $this->conn->Prepare($sql);
        $this->conn->Execute($ps,array(
<?php
		$counter = 0;
		foreach ($this->fields as $field) {
			if ($counter > 0) {
				echo '		$this->' . $field;
				if ($counter < count($this->fields) - 1) {
					echo ",\n";
				}
			}

			$counter++;
		}
?>
		));
		$sql = "select max(id) as last_id from <?=$this->table?>";
    	$rs = $this->conn->Execute($sql);
    	$this->id = $rs->fields[0];
    	return 0;
	}

<?

		#---------------------UPDATE METHOD -------------------------------------
?>
    public function update() {
		$valErrs = $this->validate() + $this->validatePK();
		if ($valErrs != 0) {
			return $valErrs;
		}
		$sql = "update <?=$this->table?> set
<?php


		$counter = 0;
		foreach ($this->fields as $field) {
			if ($counter > 0) {
				echo "		$field=?";
				if ($counter < count($this->fields) - 1) {
					echo ",";
				}
			}

			$counter++;
		}
?>
	where id = ?";
       $ps = $this->conn->Prepare($sql);
       $this->conn->Execute($ps,array(
<?php


		$counter = 0;
		foreach ($this->fields as $field) {
			if ($counter > 0) {
				echo '		$this->' . $field;
				if ($counter < count($this->fields) - 1) {
					echo ",\n";
				}
			}

			$counter++;
		}
?>,
		$this->id));
		return 0;
		
    }

    public function delete() {
		$valErrs = $this->validatePK();
		if ($valErrs != 0) {
			return $valErrs;
		}
        $sql = "delete from <?=$this->table?> where id = ?";
        $ps = $this->conn->Prepare($sql);
        $this->conn->Execute($ps,array($this->id));
        return 0;
    }

	public function load() {
		$valErrs = $this->validatePK();
		if ($valErrs != 0) {
			return $valErrs;
		}
		$sql = "select id,<?=$this->insert_cols?> from <?=$this->table?> where id = ?";
		$ps = $this->conn->Prepare($sql);
        $rs = $this->conn->Execute($ps,array($this->id));
<?php

		$counter = 0;
		foreach ($this->fields as $field) {
?>
			$this-><?=$field?> = stripslashes($rs->fields[<?=$counter?>]);
<?

			$counter++;
		}
?>
		return 0;
	}

	public function to_string() {
		$out = "<?=$this->className?>: {";
<?php

		$counter = 0;
		foreach ($this->fields as $field) {
?>
			$out .= "<?=$field?> = $this-><?=$field?>, ";
<?

			$counter++;
		}
?>		$out .= "}";
		return $out;
	}

	public function get_all($sql="") {
		if ($sql == "") {
        	$sql = "select id,<?=$this->insert_cols?> from <?=$this->table?>";
        }
        $rs = $this->conn->Execute($sql);
		$all = array();
		while (!$rs->EOF) {
			$obj = new <?=$this->className?>Model();
<?php

		$counter = 0;
		foreach ($this->fields as $field) {
?>
			$obj-><?=$field?> = stripslashes($rs->fields[<?=$counter?>]);
<?

			$counter++;
		}
?>			array_push($all,$obj);
			$rs->MoveNext();
		}
		return $all;
	}
		
<?
// ---------------------TO ARRAY METHOD -------------------------------------
?>
	function to_array() {
		$out = array();
<?php
		foreach ($this->fields as $field) {
?>
		$out["<?=$field?>"] = $this-><?=$field?>;
<?
		}
?>
		return $out;
	}
	public function validate() {
			
<?php

		$counter = 0;
		foreach ($this->fields as $field) {
			$null = $this->tableinfo[$field]['null'];
			if ($field != "id") {
?>
			if ($this-><?=$field?> == "") {
            <?php if ($null == "YES") echo "//";?>add_other_error("model-<?=$field?> is invalid.");
        	}
<?			}

			$counter++;
		}
?>
		return get_error_count();
  	}

	public function validatePK() {

		if ($this->id == "") {
			add_form_error("id is invalid.");
			return 1;
		}

	}
	

}
%>
<?
	}
}
?>