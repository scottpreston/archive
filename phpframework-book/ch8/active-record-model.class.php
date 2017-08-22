<?
class contact extends ADOdb_Active_Record {
  var $_table = 'contact';
}
$contact = new contact();
$contact->first_name = 'Scott';
$contact->last_name = 'Preston';
$contact->email = 'test@test.com';
$contact->save();
$contact = new contact();
$contactRecord = $contact->Find("email = ? ", array('test@test.com'));
