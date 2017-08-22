<?php
class PComLabelValue extends BasePcom{

	var $label;
	var $value;

    function PComLabelValue($label="",$value="") {
        $this->label = $label;
        $this->value = $value;
    }

    function create() {
    	$out = '<div class="input"><div class="label">';
    	$out .= $this->label.'</div>';
    	$out .= '<div class="field">'.$this->value.'</div></div>'."\n";
    	return $out;
    }
}
?>