<?php
require 'gentemplates/cimodel.class.php';
require 'gentemplates/basedata.class.php';
require 'gentemplates/childdata.class.php';
require 'gentemplates/controller.class.php';
require 'gentemplates/view_add.class.php';
require 'gentemplates/view_edit.class.php';
require 'gentemplates/view_list.class.php';
require 'gentemplates/view_index.class.php';

class build {

    private $root_dir;
    public $dbArray;

    public function __construct($dbArray) {
        $this->dbArray = $dbArray;
        $this->root_dir = realpath(dirname(__FILE__) . "/../..");
        //$this->root_dir = "/home/scott/tmp";
        //echo "'".$this->root_dir."'";
        //die();
    }

    public function cimodels() {
        $model_path = "/home/scott/tmp/app/models/scaffold/";
        foreach ($this->dbArray as $table_name => $table_data) {
            $fields = array();

            foreach ($table_data as $field => $attributes) {
                array_push($fields, $field);
            }

            $cimodel = new cimodel($table_name, $fields, $table_data);
            $cimodel->build($model_path . $cimodel->get_classname_from_table($table_name) . "Model.class.php");
        }
    }

    public function base_models($table = "") {
        $model_path = $this->root_dir . "/app/models/scaffold";
        mkdir ($model_path);
        mkdir ($model_path."/base");
        $model_path = $model_path."/";
        foreach ($this->dbArray as $table_name => $table_data) {
            if ($table != "") {
                if ($table == $table_name) {

                    $fields = array();
                    foreach ($table_data as $field => $attributes) {
                        array_push($fields, $field);
                    }
                    $basedata = new basedata($table_name, $fields, $table_data);
                    $basedata->build("$model_path/base/Base" . $basedata->get_classname_from_table($table_name) . "Model.class.php");
                }
            } else {

                $fields = array();
                foreach ($table_data as $field => $attributes) {
                    array_push($fields, $field);
                }
                $basedata = new basedata($table_name, $fields, $table_data);
                $basedata->build("$model_path/base/Base" . $basedata->get_classname_from_table($table_name) . "Model.class.php");
            }
        }
    }

    public function child_models($table = "") {
        $model_path = $this->root_dir . "/app/models/scaffold";
        foreach ($this->dbArray as $table_name => $table_data) {
            if ($table != "") {
                if ($table == $table_name) {

                    $fields = array();
                    foreach ($table_data as $field => $attributes) {
                        array_push($fields, $field);
                    }

                    $childdata = new childdata($table_name, $fields, $table_data);
                    $childdata->build($model_path . "/" . $childdata->get_classname_from_table($table_name) . "Model.class.php");
                }
            } else {
                $fields = array();
                foreach ($table_data as $field => $attributes) {
                    array_push($fields, $field);
                }

                $childdata = new childdata($table_name, $fields, $table_data);
                $childdata->build($model_path . "/" . $childdata->get_classname_from_table($table_name) . "Model.class.php");
            }
        }
    }

    public function models($table = "") {
        $this->base_models($table);
        $this->child_models($table);
    }

    public function controllers() {
        $controller_path = $this->root_dir . "/app/controllers/scaffold";
        mkdir($controller_path);
        foreach ($this->dbArray as $table_name => $table_data) {
            $fields = array();

            foreach ($table_data as $field => $attributes) {
                array_push($fields, $field);
            }

            $control = new controller($table_name, $fields);
            $cname = $control->get_classname_from_table($table_name);
            $cname = strtolower($cname);
            $cname = ucfirst($cname);
            $control->build($controller_path . "/" . $cname . "Controller.class.php");
        }
    }

    public function views() {
        $view_path = $this->root_dir . "/app/views/scaffold";
        mkdir ($view_path);
        $tables = array();
        foreach ($this->dbArray as $table_name => $table_data) {
            $fields = array();
            array_push($tables, $table_name);

            foreach ($table_data as $field => $attributes) {
                array_push($fields, $field);
            }

            $view_add = new view_add($table_name, $fields, $table_data);
            $view_add->build($view_path . "/" . strtolower($view_add->get_classname_from_table($table_name)) . "_add.php");

            $view_edit = new view_edit($table_name, $fields, $table_data);
            $view_edit->build($view_path . "/" . strtolower($view_edit->get_classname_from_table($table_name)) . "_edit.php");

            $view_list = new view_list($table_name, $fields);
            $view_list->build($view_path . "/" . strtolower($view_list->get_classname_from_table($table_name)) . "_list.php");
        }

        // Generate index page
        $view_index = new view_index($tables);
        $view_index->build($view_path . "/index.php");
    }

    public function all() {
        $this->models();
        $this->controllers();
        $this->views();

    }


}
