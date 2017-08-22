<?php
require_once 'builder.class.php';
class view_add extends builder {

	public $table;
	public $fielddata;
	public $className;

	function view_add($table = '', $fields = '', $tableinfo='') {
		$this->table = $table;
		$this->fielddata = $fields;
		$this->tableinfo = $tableinfo;
		$this->className = $this->get_classname_from_table($table);
	}

	function _build() {
	$tablelower = strtolower($this->className);
?>
<%php
$title = "<?=$this->className?> - add";
include_admin_header($title);
// include_header($title);
%>
<p>** Denotes Required Field</p>
<%php
$form = new PComForm("/scaffold/<?=$tablelower?>/create","create_form");
// fields start
<?
foreach ($this->fielddata as $field) {
		    $type = $this->tableinfo[$field]['type'];
		    $size = $this->tableinfo[$field]['size'];
		    $null = $this->tableinfo[$field]['null'];
		    $denotes = "";
		    if ($null != "YES") {
		        $denotes = "** ";
		    }
		    $max = $size;            
        if ($field != "id") {
		    if ($size == "") $size = 25;
		    if ($size > 50) $size = 50;
		    if ($type == "int") {
		    	$size = 10;
		    	$max = 15;
		    }
		    if ($type == "datetime" || $type == "date" || $type == "timestamp" ){
		    	$type = "date";
		    }
	?>
<? if ($type == "mediumtext" || $type == "longtext") {?>
$<?=$field?> = new PComTextarea("<?=$field?>");
<? } else if ($type == "date") {?>
$<?=$field?> = new PComDatebox("<?=$field?>");
<?}else{?>
$<?=$field?> = new PComTextbox("<?=$field?>");
$<?=$field?>->size = "<?=$size?>";
$<?=$field?>->max = "<?=$max?>";
<?} ?>
<?php if ($null == "NO") {?>
$<?=$field?>->required = true;
<? if ($type == "int") {?>
	$<?=$field?>->set_val_rule("number", "true");
<?}?>
<? if ($type == "date") {?>
	$<?=$field?>->set_val_rule("dateISO", "true");
<?}?>
$<?=$field?>->set_val_msg("required", "<?=$field?> is not valid.");
<?php } // endif null?>
$form->add($<?=$field?>);

<?		}
}?>
// -- buttons start
$butGrp = new PComButtonGroup();
$butGrp->add(new PComCancelButton());
$butGrp->add(new PComSubmitButton("Add Record"));
$form->add($butGrp);
$form->write_all();
%>

<% $form->write_validation();%>

<%
include_admin_footer();
// include_footer();
%>
<?php
	}
}
?>