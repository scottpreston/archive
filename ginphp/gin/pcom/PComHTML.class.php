<?php
class PComHTML extends BasePcom{

    var $html;

	function PComHTML($id="",$html="") {
		$this->id = $id;
		$this->html = $html;
	}

	function create() {
		$out = '<div id="'.$this->id.'" style="'.$this->style.'" class="'.$this->className.'">';
    	$out .= $this->html . '</div>';
    	return $out;
	}
}
?>