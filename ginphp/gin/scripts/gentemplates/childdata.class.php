<?php
require_once 'builder.class.php';

class childdata extends builder {

	private $table;
	private $fields;
	private $className = '';
	private $tableinfo;

	function childdata($table = '', $fields = '',$tableinfo='') {
		$this->table = $table;
		$this->fields = $fields;
		$this->tableinfo = $tableinfo;
		$cls = explode("_", $table);
		$cname = "";
		if (count($cls) > 1) {
			for ($i = 0; $i < count($cls); $i++) {
				$tmp = $cls[$i];
				$cname .= ucfirst($tmp);
			}
		} else {
			$cname = $cls[0];
		}
		$this->className = ucfirst($cname);
	}

	function _build() {?>
<%
require_once("base/Base<?=$this->className?>Model.class.php");

class <?=$this->className?>Model extends Base<?=$this->className?>Model {


public function test() {

	$temp1 = new <?=$this->className?>();
	$temp1->loadMock();
	assertValue("<?=$this->className?> model - create",0,$temp1->create());
	$temp2 = new <?=$this->className?>();
	$temp2->id = $temp1->id;
	$temp2->load();
	assertEcho("loaded value - ". $temp2->to_string()); 
	assertValue("<?=$this->className?> model - update",0,$temp1->update());
	assertValue("<?=$this->className?> model - delete",0,$temp1->delete());
}

public function loadMock()  {
<?foreach ($this->fields as $field) {
	$null = $this->tableinfo[$field]['null'];
	?>
		<?php if ($null == "YES") echo "//";?>$this-><?=$field?> = "";
<?}?>

}

}

%>
<?
	}
}
?>