<?php
class PComCancelButton extends BasePcom{
    function PComCancelButton($className = "") {
	$this -> className = $className;
    }

    function create() {
    	$out = '<button type="button" onclick="history.back();" class="'.$this->className.'">Cancel</button>';
    	return $out;
    }
}
?>