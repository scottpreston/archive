<?php
class PComSelect extends BasePcom {

    var $label;
    var $nvps;
    var $selected_value;
    var $name;
    var $size;
    var $required;
    var $onblur;

    function PComSelect($label = "", $nvps = "", $selected_value = "") {
        $this->label = $label;
        $this->name = $this->name_from_label($label);
        $this->nvps = $nvps;
        $this->selected_value = $selected_value;
    }

    function create() {
        $out = $this->start_tag();
        $out .= '<select';
        $out .= ' name="' . $this->name . '" onblur="' . $this->onblur . '">';
        $out .= '<option value=""> --- Select Option --- </option>';
        foreach ($this->nvps as $value => $name) {
            if ($value == $this->selected_value) {
                $out .= '<option value="' . $value . '" selected="selected">' . $name . '</option>';
            } else {
                $out .= '<option value="' . $value . '">' . $name . '</option>';
            }
        }
        $out .= '</select>' . "\n";
        $out .= $this->end_tag();
        return $out;
    }
}

?>