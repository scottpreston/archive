<?php
require_once 'builder.class.php';

class controller extends builder{
	private $table;
	private $fields;
	private $className = '';

	function controller($table = '', $fielddata = '') {
		$this->table = $table;
		$this->fielddata = $fielddata;
		$this->className = $this->get_classname_from_table($table);
	}

    function _build() {
    $table_lower = strtolower($this->className);
    	?>
<%
class <?=$this->className?>Controller extends BaseController{

    function __construct() {
    	parent::__construct();
    }

    // SCAFFOLD ACCESS
	public function index() {
		$<?=$this->table?> = new <?=$this->className?>Model();
		$response['results']= $<?=$this->table?>->get_all();
        return $this->render("scaffold/<?=$table_lower?>_list",$response);
	}
	
	public function add() {
	    $<?=$this->table?> = new <?=$this->className?>Model();
		$response = $<?=$this->table?>->to_array();
        return $this->render("scaffold/<?=$table_lower?>_add",$response);
	}
	
	public function edit($id) {
		$<?=$this->table?> = new <?=$this->className?>Model();
		$<?=$this->table?>->id = $id;
		$<?=$this->table?>->load();
		$response['<?=$this->table?>'] = $<?=$this->table?>;
		return $this->render("scaffold/<?=$table_lower?>_edit",$response);
	}
  
// CRUD OPERATIONS   
    public function create() {
    	$<?=$this->table?> = new <?=$this->className?>Model();
    	$<?=$this->table?>->init();
    	$result = $<?=$this->table?>->create();
    	if ($result == 0) {
    		redirect("/scaffold/<?=$table_lower?>");
    	} else {
    		// error
    		$response = $<?=$this->table?>->to_array();
            return $this->render("scaffold/<?=$table_lower?>_add");
    	}
    }
    
    public function update() {
    	$<?=$this->table?> = new <?=$this->className?>Model();
    	$<?=$this->table?>->init();
    	$result = $<?=$this->table?>->update();
    	if ($result == 0) {
    		redirect("/scaffold/<?=$table_lower?>");
    	} else {
    		// error
    		$response = $<?=$this->table?>->to_array();
    		return $this->render("scaffold/<?=$table_lower?>_edit");
    	}
    }
    
    public function delete() {
    
    	$<?=$this->table?> = new <?=$this->className?>Model();
    	$<?=$this->table?>->init();
    	$result = $<?=$this->table?>->delete();
    	if ($result == 0) {
    		redirect("/scaffold/<?=$table_lower?>");
    	} else {
    		// error
    		die("unable to delete id ".$this->id);
    	}
    }

}
    
<?
    }
}
?>