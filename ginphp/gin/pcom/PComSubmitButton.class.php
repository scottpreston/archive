<?php
class PComSubmitButton extends BasePcom {

    var $label;

    function PComSubmitButton($label = "Submit", $className = "") {
        $this->label = $label;
        $this->className = $className;
    }

    function create() {
        $out =  '<button type="submit"';
        $out .= ' name="submitbutton"';
        $out .= ' onclick="this.disable=true;"';
        $out .= ' style="' . $this->style . '"';
        $out .= ' id="'. $this->id .'"';
        $out .= ' class="' . $this->className . '">';
        $out .= $this->label . '</button>';
        return $out;
    }

}

?>