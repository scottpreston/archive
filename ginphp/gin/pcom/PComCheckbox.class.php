<?php
class PComCheckbox extends BasePcom{

	var $label;
	var $nvps;
	var $selected_values;
	var $name;
	var $size;
	var $required=true;
	var $onblur;

    function PComCheckbox($label="",$nvps=array(),$selected_values=array()) {
		$this->label = $label;
        $this->name = $this->name_from_label($label);
        $this->nvps = $nvps;
        $this->selected_values = $selected_values;
    }

    function create() {
        $out = $this->start_tag();
    	foreach ($this->nvps as $name => $value) {
    		$out .= '<input type="checkbox" name="'.$this->name.'[]" value="'.$value .'"';
    		if (in_array($value,$this->selected_values)) {
    			$out .= " checked />";
    		} else {
    			$out .= " />";
    		}
    		$out .= " ".$name."<br/>";
    	}
    	$out .= $this->end_tag();
    	return $out;
    }
}
?>