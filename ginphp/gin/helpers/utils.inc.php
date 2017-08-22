<?
function getParameter($parm) {
    $return = "";
    if (isset($_REQUEST[$parm])) {
        $return = $_REQUEST[$parm];
    }
    return $return;
}

function getSession($parm) {
    $return = "";
    if (isset($_SESSION[$parm])) {
        $return = $_SESSION[$parm];
    }
    return $return;
}

function getServer($parm) {
    $return = "";
    if (isset($_SERVER[$parm])) {
        $return = $_SERVER[$parm];
    }
    return $return;
}

function setSession($name, $value) {
    $_SESSION[$name] = $value;
}

function getCookie($parm) {
    $return = "";
    if (isset($_COOKIE[$parm])) {
        $return = $_COOKIE[$parm];
    }
    return $return;
}

function removeLastChar($str) {
    return substr($str, 0, strlen($str) - 1);
}

function InStr($String, $Find, $CaseSensitive = false) {
    $i = 0;
    $substring="";
    while (strlen($String) >= $i) {
        unset($substring);
        if ($CaseSensitive) {
            $Find = strtolower($Find);
            $String = strtolower($String);
        }
        $substring = substr($String, $i, strlen($Find));
        if ($substring == $Find) return true;
        $i++;
    }
    return false;
}

function firstLetterUpper($word) {

    $first = strtoupper(substr($word, 0, 1));
    $rest = substr($word, 1, strlen($word));
    return $first . $rest;

}

function redirect($url) {
    $hostName = $_SERVER['HTTP_HOST'];
    header("Location: http://" . $hostName . $url);
    die();
}

function save_form() {
    $old_form = array();
    foreach ($_POST as $key => $value) {
        $old_form[$key] = $value;
    }
    $_SESSION['OLD_FORM'] = $old_form;
}

function clear_form() {
    $old_form = array();
    $_SESSION['OLD_FORM'] = $old_form;
}

function slash2mysql_date($slashdate) {
    return date('Y-m-d', strtotime($slashdate));
}

function mysql2slash_date($mysqldate) {
    return date('m/d/Y', strtotime($mysqldate));
}

function mysql2year($mysqldate) {
    return date('Y', strtotime($mysqldate));
}

function mysql2date($mysqldate) {
    return date('F j, Y', strtotime($mysqldate));
}

function to_usd($num) {
    return number_format($num, 2);
}

function get_mysql_time() {
    return date("Y-m-d H:i:s");
}

function debug($str = "") {
    global $conn;
    echo $str . "<hr>Conn Error : " . $conn->ErrorMsg();
    die();
}

function ends_with($str, $sub) {
    return (substr($str, strlen($str) - strlen($sub)) == $sub);
}

function starts_with($str, $sub) {
    return strpos($str, $sub) === 0;
}

function get_passed() {
    if ($_SERVER['DOCUMENT_ROOT']!= "") {
        return ' <span style="color:green;font-weight:bold;">Passed</span><br>';
    } else {
        return " - Passed\n";
    }
}

function get_failed() {
    if ($_SERVER['DOCUMENT_ROOT'] != "") {
        return ' <span style="color:red;font-weight:bold;">Failed</span><br>';
    } else {
        return " - Failed\n";
    }
}

// -- unit testing functions -- //
function assertTrue($msg, $test_val) {
    if ($test_val) {
        echo $msg . get_passed();
    } else {
        echo $msg . get_failed();
    }
    clear_error_count();
}

function assertFalse($msg, $test_val) {
    assertTrue($msg, !$test_val);
}

function assertNumeric($msg, $test_val) {
    if (is_numeric($test_val)) {
        echo $msg . get_passed();
    } else {
        echo $msg . get_failed();
    }
    clear_error_count();
}

function assertValue($msg, $should_val, $test_val) {
    if ($should_val == $test_val) {
        echo $msg . get_passed();
    } else {
        echo $msg . ' actual ['.$test_val.'] expected ['.$should_val.']' . get_failed();
    }
    clear_error_count();
}

function assertEcho($msg) {
    echo $msg . "<br>";
    clear_error_count();
}

function dollar_format($amount) {
    $new_amount = "\$" . sprintf("%.2f", $amount);
    return $new_amount;
}

function is_image($str) {
    return (ends_with($str, '.gif') || ends_with($str, '.jpg') || ends_with($str, '.png'));
}