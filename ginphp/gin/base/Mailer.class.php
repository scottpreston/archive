<?php
require (WEB_ROOT . "/gin/lib/phpmailer/class.phpmailer.php");

class Mailer {

    private $to;
    private $from;
    private $from_address;
    private $host;
    private $username;
    private $password;
    private $subject;
    private $body;
    private $mail;
    private $port;

    function Mailer() {
        $this->mail = new PHPMailer();
        $this->mail->IsSMTP(); // set mailer to use SMTP
        $this->mail->Host = $this->host;
        $this->mail->SMTPAuth = true; // turn on SMTP authentication
        $this->mail->Username = $this->username; // SMTP username
        $this->mail->Password = $this->password; // SMTP password
        $this->mail->From = $this->from_address;
        $this->mail->FromName = $this->from;
        $this->mail->Port = $this->port;
    }

    function html_mail($to, $subj, $msg) {
        $this->mail->IsHTML(true);
        $this->mail->AddAddress($to);
        $this->mail->Subject = $subj;
        $this->mail->Body = $msg;
        $this->mail->send();

    }

    function fast_mail($to, $subj, $msg) {
        $this->mail->IsHTML(false);
        $this->mail->AddAddress($to);
        $this->mail->Subject = $subj;
        $this->mail->Body = $msg;
        $this->mail->send();
    }
}