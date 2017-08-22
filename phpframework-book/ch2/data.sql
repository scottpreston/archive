CREATE TABLE IF NOT EXISTS `contactlog` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `email` varchar(255) NOT NULL,
  `comments` mediumtext NOT NULL,
  `thetime` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=ascii AUTO_INCREMENT=1;

CREATE TABLE IF NOT EXISTS `pages` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `short_name` varchar(50) NOT NULL,
  `title` varchar(100) NOT NULL,
  `keywords` varchar(255) DEFAULT NULL,
  `abstract` mediumtext,
  `content` longtext NOT NULL,
  `publish` char(1) NOT NULL DEFAULT 'y',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;
