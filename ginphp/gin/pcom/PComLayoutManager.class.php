<?php
class PComLayoutManager extends BasePcom{

	static $VERTICAL_LAYOUT = 0;
	var $type;
	var $widgets = array();
	var $id;
	var $count;

    function PComLayoutManager($type=0) {
    	$this->type = $type;
    	$this->id = "layout_" + $this->count;
    	$this->count = $this->count+1;
    }

    function add($widget) {
    	array_push($this->widgets,$widget);
    }

    function create() {
    	$out = '<div class="outer_layout_'.$this->type.'" id="'. $this->id.'">';
    	$out .= '<div class="required">* Indicates required fields.</div>';

		foreach ($this->widgets as $widget) {
			$out .= '<div class="widget">';
			$out .= $widget->create();
			$out .='</div>';
		}

		$out .= '</div>';
		$out .= '<script language="JavaScript" type="text/javascript">
				pack();
				</script>';
		return $out;
	}
}
?>