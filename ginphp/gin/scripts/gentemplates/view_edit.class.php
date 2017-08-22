<?php
require_once 'builder.class.php';

class view_edit extends builder {

	public $table;
	public $fielddata;
	public $className;

	function view_edit($table = '', $fields = '', $tableinfo='') {
		$this->table = $table;
		$this->fielddata = $fields;
		$this->tableinfo = $tableinfo;
		$this->className = $this->get_classname_from_table($table);
	}

	function _build() {
	$tablelower = strtolower($this->className);
?>
<%php
$id = getParameter("id");
$title = "<?=$this->className?> - edit";
include_admin_header($title);
// include_header($title);
%>
<%php
$form = new PComForm("/scaffold/<?=$tablelower?>/update","update_form");

// fields start
<?		foreach ($this->fielddata as $field) {
		   $type = $this->tableinfo[$field]['type'];
		   $size = $this->tableinfo[$field]['size'];
		   $max = $size;            
        if ($field != "id") {
		    if ($size == "") $size = 25;
		    if ($size > 50)  $size = 50;
		    if ($type == "int") $size = 8;
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
<? if ($type == "int") {?>
	$<?=$field?>->set_val_rule("number", "true");
<?}?>
<? if ($type == "date") {?>
	$<?=$field?>->set_val_rule("dateISO", "true");
<?}?>
$<?=$field?>->value = $<?=$this->table?>-><?=$field?>;
<?php if ($null == "NO") {?>
$<?=$field?>->required = true;
<? if ($type == "int") {?>
	$<?=$field?>->set_val_rule("number", "true");
<?}?>
<? if ($type == "date") {?>
	$<?=$field?>->set_val_rule("dateISO", "true");
<?}?>
$<?=$field?>->set_val_msg("required", "<?=$field?> is not valid.");
<?php }?>
$form->add($<?=$field?>);
<?       
        }
}
?>

$hidden = new PComHidden("id",$<?=$this->table?>->id);
$hidden->write();
$form->add($hidden);

// -- buttons start
$butGrp = new PComButtonGroup();
$butGrp->add(new PComCancelButton("Cancel"));
$butGrp->add(new PComSubmitButton("Update Record"));
$delBut = new PComRegularButton("Delete Record");
$delBut->onclick = "deleteme()";
$butGrp->add($delBut);
$form->add($butGrp);
$form->write_all();
%>

<script type="text/javascript">
function deleteme() {
	if (confirm("Are you sure you want to delete this?")) {
		var frm = document.getElementById("update_form");
		frm.action = "/scaffold/<?=$tablelower?>/delete";
		frm.submit();
	}
}
</script>


<% $form->write_validation();%>

<%
include_admin_footer();
// include_footer();
%>
<?php

	}
}
?>