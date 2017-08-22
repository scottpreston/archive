<?php
class PComTextbox extends BasePcom {

    var $value;
    var $size;
    var $max;

    function PComTextbox($label = "", $value = "") {
        $this->label = $label;
        $this->name = $this->name_from_label($label);
        $this->value = $value;
    }

    function create() {
        $out = $this->start_tag();
        $out .= '<input type="text"';
        $out .= ' size="' . $this->size . '"';
        $out .= ' name="' . $this->name . '"';
        $out .= ' value="' . $this->value . '"';
        $out .= ' id="' . $this->name . '"';
        $out .= ' class="' . $this->className . '"';
        $out .= ' maxlength="' . $this->max . '"';
        $out .= '/>' . $this->end_tag();
        return $out;
    }
}