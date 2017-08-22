<?php
class PComSummaryItem extends BasePcom{

    var $label;
	var $value;

	function PComSummaryItem($label="",$value="") {
        $this->label = $label;
        $this->value = $value;
     }

    function create() {
        $out = $this->start_tag();
    	$out .= '<div class="'.$this->className.'" style="'.$this->style.'">'.$this->value.'</div>';
    	$out .= $this->end_tag();
    	return $out;
    }
}
?>