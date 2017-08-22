<?php
abstract class BasePcom {

	var $label;
	var $name;
	var $required = false;
	var $id;
	var $className;
	var $style;

	// validation variables
	var $val_rules = array();
	var $val_msgs = array();

	private $required_className = "required";

	function BasePcom() {
	}

	abstract function create();

	function name_from_label($label) {
		$name = strtolower($label);
		return str_replace(" ","_",$name);
	}

	function start_tag() {
		$out = '<div class="input"><div class="label">';
		$out .= $this->label;
		if ($this->required) {
			$this->className = $this->className .' '. $this->required_className;
			$out .= '<span class="req"> (required) </span>';
		}
		$out .= '</div><div class="field">';
		return $out;
	}

	function end_tag(){
		return '</div></div>'."\n";
	}

	function write() {
		echo $this->create();
	}

	function set_val_rule($name,$val) {
		$this->val_rules[$name] = $val;
	}

	function set_req_msg($msg) {
		$this->val_msgs["required"] = $msg;
	}
	
	function set_val_msg($name,$val) {
		if (isset($this->val_msgs["required"]) == false) {
			$this->val_msgs["required"] = 'Please provide a '.$this->name .'.';
		}
		$this->val_msgs[$name] = $val;
	}

	function get_req() {
		if ($this->required) {
			return "true";
		} else {
			return "false";
		}
	}

	function get_val_rule(){
		$out = '';
		$out = $this->name.': {required: '.$this->get_req().',';
		$out2 = "";
		if (count($this->val_rules) > 0) {
			foreach ($this->val_rules as $name=>$value) {
				$out2 .= $name. ':' . $value .',';
			}
			$out2 = substr($out2, 0,-1);
		} else {
			$out = substr($out, 0,-1);
		}
		$out .= $out2 . "}";

		return $out;
	}

	function get_val_msg() {
		$out = '';
		if (count($this->val_msgs) == 0) {
			$out = $this->name . ': "Please provide a '.$this->name .'"';
			return $out;
		}
		if (count($this->val_msgs) == 1) {
			$out = $this->name . ': "'.$this->val_msgs['required'] .'"';
			return $out;
		}
		if (count($this->val_msgs) > 1) {
			$out = $this->name.': {';
			foreach ($this->val_msgs as $name=>$value) {
				$out .= $name. ': "' . $value .'",';
			}
			$out = substr($out, 0,-1);
		}
		$out .= "}\n";

		return $out;
	}

}
?>