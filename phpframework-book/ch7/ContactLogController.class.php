<?
class ContactlogController extends BaseController {

    function __construct() {
    	parent::__construct();
    }

	public function index() {
		$contactlog = new ContactlogModel();
		$response['results']= $contactlog->get_all();
        return $this->render("scaffold/contactlog_list",$response);
	}
}