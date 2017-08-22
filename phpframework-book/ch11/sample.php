<?
$form = new PComForm("/accounts/login","login_form");

$form->add();
$email = new PComTextbox("Email",getParameter("email"));
$email->label = "Email Address?";
$email->size = 50;
$email->required = true;
$email->set_val_rule("email", "true");
$email->set_val_msg("required", "Please enter a valid email address.");
$form->add($email);

$password = new PComPassword("Password",getParameter("password"));
$password->size = 16;
$password->required = true;
$password->set_val_rule("minlength", "6");
$password->set_val_msg("minlength",
"Your password must be at least 6 characters long.");
$form->add($password);

$butGrp = new PComButtonGroup();
$submit = new PComSubmitButton("Login");
$submit->className = "btn btn-green";
$submit->id = "login_button";
$butGrp->add($submit);
$form->add($butGrp);

$form->write_all();

