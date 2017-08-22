<?php
class PComDatebox extends BasePcom{

	var $label;
	var $value;
	var $name;
	var $size;
	var $required=true;
	var $onblur;

	function PComDatebox($label="",$value="") {
		$this->label = $label;
        $this->name = $this->name_from_label($label);
        $this->value = $value;
    }

    function create() {
    	$out = $this->start_tag();
    	$out .= '<input type="text" size="'.$this->size.'" ';
    	$out .= ' name="'.$this->name.'" value="'.$this->value.'" id="'.$this->name.'"/>';
    	//$out .= '<a href="javascript:void(0);" onclick="show_cal();">';
		//$out .= '<img class="ui-datepicker-trigger" src="/css/jquery/base/images/calendar.gif" alt="..." title="..." border="0"/></a>'."\n";
		$out .= $this->end_tag();
    	$out .= $this->createJS();
    	return $out;
    }
    
    function createJS() {
    	return '<script>$(function() {$("#'.$this->name.'" ).datepicker({ dateFormat: "yy-mm-dd" });});</script>';
    }
}
?>