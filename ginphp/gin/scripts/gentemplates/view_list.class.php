<?php
require_once 'builder.class.php';

class view_list extends builder {
	private $table;
	private $fields;
	private $className = '';
	function view_list($table = '', $fielddata = '') {
		$this->table = $table;
		$this->fielddata = $fielddata;
		$this->className = $this->get_classname_from_table($table);
	}

	function _build() {
	// TEMPLATE STARTS --------------------------------------------------
    $tablelower = strtolower($this->className);
?>
<%
$title = "<?=$tablelower?> - list";
include_admin_header($title);
// include_header($title);
%>
<p><a href="/scaffold/<?=$tablelower?>/add">Add New Record</a></p>
<table border="1">
	<tr>
<?		foreach ($this->fielddata as $field) {?>
		<th><?=$field?></th>
<?}?>
	</tr>
<% foreach ($results as $obj) { %>
	<tr>
	<?		foreach ($this->fielddata as $field) {
		if ($field == 'id') {
		?>
		<td align="center"><a href="/scaffold/<?=$tablelower?>/edit/<%=$obj->id%>"><%=$obj->id%></a></td>
		<?}else{?>
		<td><%=stripslashes($obj-><?=$field?>)%></td>
		<?}?>
	<?}?>
	</tr>
<% }%>
</table>
<%
include_admin_footer();
// include_footer();
%>
<?
	// TEMPLATE ENDS --------------------------------------------------
	}
}
?>