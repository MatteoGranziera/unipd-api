<?php
	include 'simple_html_dom.php';
	$uni_URL = 'http://www.unipordenone.it';
	$calendar_path = '/mrbs/day.php';

	function GetHTMLCalendar($day, $month, $year){
		$post_array = array();

		if($day != null)
			$post_array["day"] = $day;

		if($month != null)
			$post_array["month"] = $day;

		if($year != null)
			$post_array["year"] = $day;

		$html = file_get_html($uni_URL . $calendar_path, $post_array);

		return $html;
	}

	function GetInformations($day, $month, $year){
		
	}



?>