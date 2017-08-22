<?php
require_once 'builder.class.php';

class view_index extends builder {

	public $tables;

	function view_index($tables) {
		$this->tables = $tables;
	}

	function _build() {
?>
<%
$title = "Scaffold Home Page";
include_admin_header($title);
%>
<?	foreach ($this->tables as $table) {

	$table = $this->get_classname_from_table($table);
	$table = strtolower($table);
	?>
	<p><a href="/scaffold/<?=$table?>"><?=$table?></a> home page.</p>
<?}?>
<%
include_admin_footer();
%>
<?php
	}
}