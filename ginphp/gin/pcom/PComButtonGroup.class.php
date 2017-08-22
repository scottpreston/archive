<?php
class PComButtonGroup extends BasePcom{

	var $buttons = array();

    function PComButtonGroup() {
    }

    function add($button) {
    	array_push($this->buttons,$button);
    }

    function create() {
    	$out = '<div class="button_group">';
		foreach ($this->buttons as $button) {
			$out .= $button->create();
		}
		$out .='</div>';
		return $out;
    }
}
?>