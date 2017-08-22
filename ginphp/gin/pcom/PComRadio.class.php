<?php
class PComRadio extends BasePcom{

	var $label;
	var $nvps;
	var $selected_value;
	var $name;
	var $size;
	var $required;
	var $onblur;

    function PComRadio($label="",$nvps,$selected_value) {
		$this->label = $label;
        $this->name = $this->name_from_label($label);
        $this->nvps = $nvps;
        $this->selected_value = $selected_value;
    }

    function create() {
    	
    	$out = $this->start_tag();
    	foreach ($this->nvps as $name => $value) {
    		$out .= '<input type="radio" name="'.$this->name.'" value="'.$value .'"';
    		if ($this->selected_value == $value) {
    			$out .= " checked /> ";
    		} else {
    			$out .= " /> ";
    		}
    		$out .= $name;
    		$out .= "<br />";
    	}
    	$out .= $this->end_tag();
    	return $out;
    }
}
?>