<?php
class PComHidden extends BasePcom{

	var $name;
	var $value;

    function PComHidden($name="",$value="") {
        $this->name = $name;
        $this->value = $value;
    }

    function create() {
    	$out = '<input type="hidden" name="'.$this->name.'" value="'.$this->value.'"/>'."\n";
    	return $out;
    }
}
?>