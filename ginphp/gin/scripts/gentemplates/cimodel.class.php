<?php
require_once 'builder.class.php';

class cimodel extends builder {

	private $table;
	private $fields;
	private $tableinfo;
	private $className = '';
	private $questionMarks = '';
	private $insert_cols = '';

	function cimodel($table = '',$fields, $tableinfo='') {
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
class <?=$this->className?>Model extends Model {
<?
$field_string = "";
foreach ($this->fields as $field) {
	$field_string = $field_string. "$" . $field . ",";
	?>
	var $<?=$field?>;
<?}
$field_string = substr($field_string, 0,-1);
?>

    public function __construct() {
        parent::__construct();
    }

<?

		#---------------------INSERT METHOD -------------------------------------
?>
	// since insert is still there...
	public function create() {
		$this->insert();
	}

    public function insert() {
		$valErrs = $this->validate();
		if ($valErrs != 0) {
			return $valErrs;
		}
        $this->db->insert('<?=$this->table?>', $this);
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
        $this->db->where('id', $this->id);
		$this->db->update('<?=$this->table?>', $this);
		return 0;

    }

    public function delete() {
		$valErrs = $this->validatePK();
		if ($valErrs != 0) {
			return $valErrs;
		}
        $this->db->delete('<?=$this->table?>', array('id' => $this->id));
        return 0;
    }

	public function load() {
		$valErrs = $this->validatePK();
		if ($valErrs != 0) {
			return $valErrs;
		}
		$query = $this->db->get_where('<?=$this->table?>', array('id' => $this->id), 1);
		$result = $query->result();
<?php

		$counter = 0;
		foreach ($this->fields as $field) {
?>
			$this-><?=$field?> = stripslashes($result[0]-><?=$field?>);
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

    function get_by_id($id) {
		$query = $this->db->get_where('<?=$this->table?>', array('id' => $this->id), 1);
		$result = $query->result_array();
		return $result[0];
	}

	public function get_all($sql="") {
	    //$this->db->order_by("thetime", "desc");
		$query = $this->db->get('<?=$this->table?>');
		return $query->result_array();
	}

    function count_all() {
        return $this->db->count_all('<?=$this->table?>');
    }


	public function validate() {

<?php

		$counter = 0;
		foreach ($this->fields as $field) {
			$null = $this->tableinfo[$field]['null'];
			if ($field != "id") {
?>
			if ($this-><?=$field?> == "") {
            <?php if ($null == "YES") echo "//";?>add_form_error("<?=$field?> is invalid.");
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
		}
		return get_error_count();
	}

	public function test() {


	}

}
%>
<?
	}
}
?>