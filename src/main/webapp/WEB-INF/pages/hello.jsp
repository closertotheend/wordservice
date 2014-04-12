<!DOCTYPE html>
<html lang="en" ng-app="wordApp">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>WordService</title>

    <!-- Bootstrap core CSS -->
    <link href="../../resources/modules/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <script src="../../resources/modules/jquery/jquery.min.js"></script>
    <script src="../../resources/modules/bootstrap/js/bootstrap.min.js"></script>
    <script src="../../resources/modules/angularjs/angular.min.js"></script>
    <script src="../../resources/modules/angularjs/angular-route.min.js"></script>
    <script src="../../resources/modules/angularjs/angular-resource.min.js"></script>

    <!-- APP STUFF -->
    <script src="../../pages/js/app.js"></script>
    <script src="../../pages/js/controllers.js"></script>
    <script src="../../pages/js/services.js"></script>

    <style>
        body {
            padding-top: 50px;
            padding-bottom: 20px;
            background-image:url('../../resources/images/body.png');
        }
    </style>
    <script>
        $(document).ready(function(){
            $(".navbar li").click(function(){
                $(".navbar li").removeClass("active");
                $(this).addClass("active");
            });
        });
    </script>
</head>

<body>

<div class="navbar navbar-default navbar-fixed-top" role="navigation">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">WordServiceAPI</a>
        </div>
        <div class="collapse navbar-collapse">
            <ul class="nav navbar-nav">
                <li class="active"><a href="#">Home</a></li>
                <li><a href="#use-example">Use Example</a></li>
                <li><a href="#extend">Extend API</a></li>
                <li><a href="#analyze">Analyze Word</a></li>
                <li><a href="#about">Statistics</a></li>
                <li><a href="#contact">Legal Notes</a></li>
            </ul>
        </div>
        <!-- /.nav-collapse -->
        <!--/.navbar-collapse -->
    </div>
</div>

<div ng-view></div>

<div class="container">
    <div class="row">
        <div class="col-md-12">
            <hr>
            <footer>
                <p>&copy; Ilja Gužovski 2014, Template is taken from FromBootsWatch.com</p>
            </footer>
        </div>
    </div>
</div>

</body>
</html>