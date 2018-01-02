/* document ready
/***********************************************************/
$(function() {
    $("#general").parent().on("click", function() {
        setTimeout(function() {
            $("#sysBodyWrap").css("height", $(".general").height()+150);
        }, 300);
        if ($(this).hasClass("active")) {
            return;
        } else {
            $(this).addClass("active");
            $("#business").parent().removeClass("active");
            $(".business").removeClass("active");
            $(".general").addClass("active");

            $(".general").css("left", '-100%');
            $(".general").animate({
                left: 0
            }, 300);
            $(".business").animate({
                left: '100%'
            }, 300);
        }
    });

    $("#business").parent().on("click", function() {
        setTimeout(function() {
            console.log("gen"+$(".general").height());
            console.log("bus"+$(".business").height());
            console.log($("#sysBodyWrap").height());
            $("#sysBodyWrap").css("height", $(".business").height());
        }, 300);
        if ($(this).hasClass("active")) {
            return;
        } else {
            $(this).addClass("active");
            $("#general").parent().removeClass("active");
            $(".general").removeClass("active");
            $(".business").addClass("active");

            $(".business").css("left", '100%');
            $(".business").animate({
                left: 0
            }, 300);

            $(".general").animate({
                left: '-100%'
            }, 300);
        }
    });

    // 通用英语课程架构切换
    $("#sysStructureItems span").on({
        "mouseenter": function() {
            $("#sysStructureItems span").removeClass("active");
            $(this).addClass("active");
            $(".general .structure-intro").removeClass("active");
            $(".general .structure-intro").eq($(this).index()).addClass("active");
        }
    });

    // 通用英语中，Lecture主题展示
    $("#genLectureTheme").on("click", function() {
        $("#sysBodyWrap").css("height", "auto");
        $("#generalOtoThemes").slideUp();
        $("#genOtoTheme").removeClass("icon-minus").addClass("icon-plus");
        if ($(this).hasClass("icon-plus")) {
            $(this).removeClass("icon-plus").addClass("icon-minus");
            $("#generalLectureThemes").slideDown();
        } else if ($(this).hasClass("icon-minus")) {
            $("#generalLectureThemes").slideUp();
            $(this).removeClass("icon-minus").addClass("icon-plus");
        }
    });

    // 通用英语中，oto主题展示
    $("#genOtoTheme").on("click", function() {
        $("#sysBodyWrap").css("height", "auto");
        $("#generalLectureThemes").slideUp();
        $("#genLectureTheme").removeClass("icon-minus").addClass("icon-plus");
        if ($(this).hasClass("icon-plus")) {
            $(this).removeClass("icon-plus").addClass("icon-minus");
            $("#generalOtoThemes").slideDown();
        } else if ($(this).hasClass("icon-minus")) {
            $("#generalOtoThemes").slideUp();
            $(this).removeClass("icon-minus").addClass("icon-plus");
        }
    });

    // 通用英语，Lecture级别内容切换
    $("#generalLectureLevel").delegate("label", "mouseenter", function() {
        $("#generalLectureLevel label").removeClass("active");
        $(this).addClass("active");
        $(".general-lec-theme").removeClass("active");
        $(".general-lec-theme").eq($(this).index()).addClass("active");
    });

    // 通用英语，Oto级别内容切换
    $("#generalOtoLevel").delegate("label", "mouseenter", function() {
        $("#generalOtoLevel label").removeClass("active");
        $(this).addClass("active");
        $(".general-oto-theme").removeClass("active");
        $(".general-oto-theme").eq($(this).index()).addClass("active");
    });

    // 商务英语课程架构切换
    $("#sysStructureItems2 span").on({
        "mouseenter": function() {
            $("#sysStructureItems2 span").removeClass("active");
            $(this).addClass("active");
            $(".business .structure-intro").removeClass("active");
            $(".business .structure-intro").eq($(this).index()).addClass("active");
        }
    });

    // 商务英语中，Lecture主题展示
    $("#busLectureTheme").on("click", function() {
        $("#sysBodyWrap").css("height", "auto");
        $("#businessOtoThemes").slideUp();
        $("#busOtoTheme").removeClass("icon-minus").addClass("icon-plus");
        if ($(this).hasClass("icon-plus")) {
            $(this).removeClass("icon-plus").addClass("icon-minus");
            $("#businessLectureThemes").slideDown();
        } else if ($(this).hasClass("icon-minus")) {
            $("#businessLectureThemes").slideUp();
            $(this).removeClass("icon-minus").addClass("icon-plus");
            $("#sysBodyWrap").animate({
                "height": $(".business").height()-400
            }, 300);
        }
    });

    // 商务英语中，oto主题展示
    $("#busOtoTheme").on("click", function() {
        $("#sysBodyWrap").css("height", "auto");
        $("#businessLectureThemes").slideUp();
        $("#busLectureTheme").removeClass("icon-minus").addClass("icon-plus");
        if ($(this).hasClass("icon-plus")) {
            $(this).removeClass("icon-plus").addClass("icon-minus");
            $("#businessOtoThemes").slideDown();
        } else if ($(this).hasClass("icon-minus")) {
            $("#businessOtoThemes").slideUp();
            $(this).removeClass("icon-minus").addClass("icon-plus");
            $("#sysBodyWrap").animate({
                "height": $(".business").height()-400
            }, 300);
        }
    });

    // 商务英语，Lecture级别内容切换
    $("#businessLectureLevel").delegate("label", "mouseenter", function() {
        $("#businessLectureLevel label").removeClass("active");
        $(this).addClass("active");
        $(".business-lec-theme").removeClass("active");
        $(".business-lec-theme").eq($(this).index()).addClass("active");
    });

    // 商务英语，Oto级别内容切换
    $("#businessOtoLevel").delegate("label", "mouseenter", function() {
        $("#businessOtoLevel label").removeClass("active");
        $(this).addClass("active");
        $(".business-oto-theme").removeClass("active");
        $(".business-oto-theme").eq($(this).index()).addClass("active");
    });

    var classSystemData = {
        "generalLecture": {
            "level1": [
                "Greetings and Introductions",
                "Writing and Punctuation",
                "My Friend",
                "What do you do everyday?",
                "Languages and Abilities",
                "Weather and Destination",
                "My Job",
                "My home",
                "Opposites",
            ],
            "level2": [
                "Body Parts",
                "Clothes and Fashion",
                "How People Look",
                "Preferences",
                "My Favorites",
                "Hobbies and Activities",
                "Describing Your Day",
                "Important Dates",
                "Find the Way",
            ],
            "level3": [
                "Express Yourself",
                "Families (Relationships)",
                "Vacations",
                "City Life",
                "Describing Things",
                "Objects and Colors",
                "Where Things Are",
                "Seasons and Vacations",
                "Food and Supermarket",
            ],
            "level4": [
                "Time Share Investment",
                "Chinese Weddings",
                "Friends & Aquaintances",
                "Travel",
                "Conversational Skills",
                "Phone Etiquete",
                "Giving Instructions",
                "a Failing Company",
                "At the Office",
            ],
            "level5": [
                "Simple Questions & Follow Up Questions",
                "Giving Information",
                "Customer Service",
                "Creative Writing",
                "the Business Meal",
                "Trend Forecasting",
                "Information Sharing",
                "Pronunciations and Intonations",
                "Key Introduction",
            ],
            "level6": [
                "Team Exercises",
                "Why Work?",
                "Scheduling",
                "Asking the Right Question",
                "Automobiles",
                "Tense Trips",
                "Statistics & Data",
                "Preparing for Meetings",
                "Perspective",
            ],
            "level7": [
                "Hotel",
                "A New Idea",
                "The World of Work",
                "Value and Worth",
                "Direct vs. Indirect Speech",
                "The Art of Persuading",
                "Giving Advice",
                "Business Phrasal Verbs",
                "Biz Meeting Activity: The Training Budget",
            ],
            "level8": [
                "Talking about the Weather",
                "The Desert Island",
                "Good Grammar Can Make You money",
                "The Beach",
                "Emphasis in Speech",
                "At an Auction",
                "Finding a Place to Live",
                "Adverbs",
                "Modes of Transportation",
            ],
            "level9": [
                "Past Activities",
                "Talking about Work",
                "Traveling for Business",
                "Bad Directions",
                "Presentation Skills",
                "Figurative Language",
                "Telephone Skills",
                "Callers and Receptionists",
                "Projects",
            ],
            "level10": [
                "Logos, Slohans, Brands & Mission Statements",
                "Business Ethics",
                "the Horrible Truth about Business",
                "Luxury Perfumes",
                "Leadership",
                "Effective Communication",
                "Business Plans",
                "Complaints",
                "Finding Truth",
            ],
            "level11": [
                "Startup Businesses",
                "Interviews & Resumes",
                "Managing a Project",
                "the Workplace",
                "Intentions",
                "Breakfast Oround the World",
                "Cooking and Recipes",
                "Fashion in the Work place",
                "Fashion",
            ],
            "level12": [
                "Trouble-Shooter",
                "the Monetary System",
                "Promoting Tourism",
                "Staying Heathy",
                "Asking Questions",
                "at the Movies",
                "Small Talk & Party Chat",
                "No smoking",
                "Travel Plannning",
            ],
            "level13": [
                "Planning Ahead",
                "Where's the Smart Money?",
                "Achievements",
                "Describing Trends",
                "On a Trip",
                "Checking In",
                "Work-Life Balance",
                "Presentation Skills",
                "Meeting Practice 1"
            ],
            "level14": [
                "Dealing with Mistakes",
                "Email Skills",
                "Work Experience",
                "Office Work",
                "Making an Appointment",
                "Manufacturing and Trade",
                "The Interview Panel",
                "Graphic Information",
                "Keeping in Touch",
            ],
            "level15": [
                "Airport",
                "Corporate Culture",
                "Anger is Heat",
                "Managing the Future",
                "Feeble Excuses",
                "Customer Service 1",
                "Servicing a Debt",
                "Business Meeting Activity: Quality and Personnel",
                "Selling Off A Line Of Business",
            ],
            "level16": [
                "Perplexing Problems",
                "Business lunch",
                "New Customer",
                "Company Descriptions",
                "Product Comparisons",
                "Payment Methods",
                "Negotiating",
                "Quotations",
                "What's your Viewpoint?",
            ],
            "level17": [
                "Decision Making",
                "Meeting Practice 2",
                "Points in a Negotiation",
                "Law and Oder",
                "Projects",
                "Negotiating Contracts",
                "Business Idioms",
                "Decision Maker Activity: Hard Times",
                "Working Backwards",
            ],
            "level18": [
                "Decision Making",
                "Case Study - Federal Motors",
                "Intellectual Property",
                "Trying to be Polite",
                "How to build an Argument Pyramid",
                "Let's Go: China",
                "Human Resources Management",
                "Negotiating 2",
                "Customer Service 2",
            ]
        },
        "generalOto": {
            "level1": [
                "Greetings",
                "The Alphabet",
                "Introductions",
                "Age",
                "Countries",
                "Nationalities",
                "Occupations",
                "My Family",
                "Pets",
            ],
            "level2": [
                "The Body",
                "Clothes",
                "Appearance",
                "Feelings",
                "Likes and Dislikes",
                "Activities",
                "Days of the Week",
                "The Date",
                "The Office",
            ],
            "level3": [
                "Introductions",
                "The Family",
                "Going on Vacation",
                "A City Tour",
                "Descriptions",
                "Objects & Animals",
                "Adjectives",
                "Vacation Plans",
                "The Supermarket",
            ],
            "level4": [
                "The Summer Rental",
                "A Party",
                "Brunch with a friend",
                "Coming to America",
                "The Cocktail Party (phone number)",
                "On the Phone",
                "Reserving a Flight",
                "First Day at Work",
                "At Reception",
            ],
            "level5": [
                "A Delivery",
                "Visitor Information",
                "Customer Service Questions",
                "Email",
                "The Bill (restaurant)",
                "The Presentation Opening",
                "The Memo",
                "Deadlines",
                "Phone Introductions"
            ],
            "level6": [
                "The Rescheduling",
                "Work Review",
                "The Schedule",
                "Vacation Request",
                "Renting a Car",
                "Ticket to Boston",
                "Numbers & Money",
                "First Meeting",
                "Appointments",
            ],
            "level7": [
                "Reserving a Room",
                "Conference Plans",
                "An Interview",
                "Presenting your Job",
                "Days & Hours",
                "Giving your Opinion",
                "Solving the Problem",
                "Welcoming a Visitor",
                "The Conference",
            ],
            "level8": [
                "Weather Report",
                "Vacation Time",
                "The Canoe Trip",
                "At the Shore",
                "Arrival in New York",
                "The East Coast",
                "The Housing Search",
                "Visiting a House",
                "Buying a Car",
            ],
            "level9": [
                "The Gas Station",
                "The Company",
                "At the Airport",
                "Getting Direction",
                "Product Presentation",
                "The Welcome Desk",
                "Appointment Setup",
                "Before the Visit",
                "About the Project",
            ],
            "level10": [
                "Company Presentation",
                "Registration",
                "Fair Registration",
                "A Special Offer",
                "A Trade Fair",
                "First Negotiations",
                "Contracts",
                "Technical Problems",
                "Security Rules",
            ],
            "level11": [
                "Contract Settlements",
                "Job Interview",
                "The Survey",
                "Advice",
                "Instructions",
                "Breakfast Menu",
                "Desserts",
                "Window-shopping",
                "The Fitting Room",
            ],
            "level12": [
                "An Emergency",
                "Banks and ATMs",
                "Correspondence",
                "Still Hungry",
                "The Menu",
                "At the Doctors",
                "Paying a Bill (utility bills)",
                "Living in America",
                "Seeing the USA",
            ],
            "level13": [
                "Seminar Planning (+G)",
                "Insurance & Banking",
                "A Project Update",
                "Policies",
                "Site Visit",
                "Hotel Brochures",
                "The Resignation",
                "Business Negotiations",
                "A Meeting to Reschedule (see lev. 6)"
            ],
            "level14": [
                "The Complaint",
                "The Response",
                "A New Job",
                "Job Promotions",
                "Plane Reservations (see lev. 4)",
                "Delivery Time",
                "Delegates",
                "Market Research",
                "Helpful Contacts",
            ],
            "level15": [
                "Flight Information",
                "Company Strategy",
                "A Difficult Visitor",
                "A Busy Thursday Morning",
                "A Defective Product",
                "Service Problems",
                "Project Risks",
                "A Green Challenge",
                "Sales Training Analysis",
            ],
            "level16": [
                "Trouble with Orders",
                "Organizing a Stay",
                "Welcoming Visitors",
                "The Company Stand",
                "Comparing Products",
                "Making a Sale",
                "Negotiating",
                "Handling an Invoice",
                "A Disagreement",
            ],
            "level17": [
                "An Advertising Strategy",
                "Technical Explanations",
                "A Salary Negotiation",
                "Legal Advice",
                "The Project",
                "Client Negotiations",
                "The Economy",
                "The Press Article",
                "Department Restructure",
            ],
            "level18": [
                "The Decision",
                "Product Analysis",
                "Information Security",
                "Artistic Differences",
                "A Convincing Argument",
                "End-of-Year Summary",
                "A Recruitment Strategy",
                "Sales Training Negotiation",
                "Customer Service Objectives",
            ]
        },
        "businessLecture": {
            "level1": [
                "Phone Etiquete",
                "Giving Instructions",
                "Making Conversation",
                "a Failing Company",
                "Travel",
                "At the Office",
                "Simple Questions & Follow Up Questions",
                "Giving Information",
                "Customer Service",
            ],
            "level2": [
                "Creative Writing",
                "the Business Meal",
                "Trend Forecasting",
                "Information Sharing",
                "Pronunciations and Intonations",
                "Key Introduction",
                "Team Exercises",
                "Why Work?",
                "Scheduling",
            ],
            "level3": [
                "Asking the Right Question",
                "Automobiles",
                "Tense Trips",
                "Statistics & Data",
                "Preparing for Meetings",
                "Perspective",
                "Hotel",
                "A New Idea",
                "The World of Work",
            ],
            "level4": [
                "Value and Worth",
                "Direct vs. Indirect Speech",
                "The Art of Persuading",
                "Giving Advice",
                "Business Phrasal Verbs",
                "Biz Meeting Activity: The Training Budget",
                "Talking about Work",
                "Traveling for Business",
                "Breakfast Oround the World",
            ],
            "level5": [
                "Bad Directions",
                "Presentation Skills",
                "Figurative Language",
                "Telephone Skills",
                "Callers and Receptionists",
                "Projects",
                "Logos, Slohans, Brands & Mission Statements",
                "Business Ethics",
                "the Horrible Truth about Business",
            ],
            "level6": [
                "Luxury Perfumes",
                "Leadership",
                "Effective Communication",
                " Business Plans",
                "Complaints",
                "Finding Truth",
                "Startup Businesses",
                "Interviews & Resumes",
                "Managing a Project",
            ],
            "level7": [
                "the Workplace",
                "Intentions",
                "Achievements",
                "Describing Trends",
                "On a Trip ",
                "Checking In",
                "Work-Life Balance",
                "Presentation Skills",
                "Meeting Practice 1",
            ],
            "level8": [
                "Dealing with Mistakes ",
                "Email Skills ",
                "Work Experience",
                "Office Work ",
                "Making an Appointment",
                "Manufacturing and Trade ",
                "The Interview Panel",
                "Graphic Information",
                "Keeping in Touch ",
            ],
            "level9": [
                "Airport",
                "Corporate Culture",
                "Anger is Heat",
                "Managing the Future",
                "Feeble Excuses",
                "Customer Service 1",
                "Servicing a Debt",
                "Business Meeting Activity: Quality and Personnel",
                "Selling Off A Line Of Business",
            ],
            "level10": [
                "Perplexing Problems",
                "Business lunch ",
                "New Customer",
                "Company Descriptions",
                "Product Comparisons",
                "Payment Methods",
                "Negotiating ",
                "Quotations",
                "What's your Viewpoint?",
            ],
            "level11": [
                "Decision Making",
                "Meeting Practice 2",
                "Points in a Negotiation",
                "Law and Oder",
                "Projects",
                "Negotiating Contracts",
                "Business Idioms",
                "Decision Maker Activity: Hard Times",
                "Working Backwards",
            ],
            "level12": [
                "Decision Making",
                "Case Study - Federal Motors",
                "Intellectual Property",
                "Trying to be Polite",
                "How to build an Argument Pyramid.",
                "Let's Go: China ",
                "Human Resources Management",
                "Negotiating 2",
                "Customer Service 2",
            ]
        },
        "businessOto": {
            "level1": [
                "On the Phone",
                "Reserving a Flight",
                "Information by Phone",
                "First Day at Work ",
                "Meeting the Boss",
                "At Reception",
                "A Delivery ",
                "Visitor Information",
                "Customer Service Questions",
            ],
            "level2": [
                "Email",
                "The Bill ",
                "The Presentation Opening ",
                "The Memo",
                "Deadlines",
                "Phone Introductions",
                "The Rescheduling",
                "Work Review",
                "The Schedule ",
            ],
            "level3": [
                "Vacation Request  ",
                "Renting a Car",
                "Ticket to Boston",
                "Numbers & Money",
                "First Meeting",
                "Appointments",
                "Reserving a Room",
                "Conference Plans",
                "An Interview",
            ],
            "level4": [
                "Presenting your Job",
                "Days & Hours",
                "Giving your Opinion",
                "Solving the Problem",
                "Welcoming a Visitor",
                "The Conference",
                "The Company",
                "At the Airport",
                "Breakfast Menu",
            ],
            "level5": [
                "Getting Direction",
                "Product Presentation",
                "The Welcome Desk",
                "Appointment Setup",
                "Before the Visit",
                "About the Project",
                "Company Presentation",
                "Registration",
                "Fair Registration",
            ],
            "level6": [
                "A Special Offer",
                "A Trade Fair ",
                "First Negotiations",
                "Contracts ",
                "Technical Problems",
                "Security Rules ",
                "Contract Settlements ",
                "Job Interview  ",
                "The Survey ",
            ],
            "level7": [
                "Advice ",
                "Instructions ",
                "A Project Update  ",
                "Policies   ",
                "Site Visit  ",
                "Hotel Brochures",
                "The Resignation ",
                "Business Negotiations",
                "A Meeting to Reschedule ",
            ],
            "level8": [
                "The Complaint  ",
                "The Response",
                "A New Job ",
                "Job Promotions ",
                "Plane Reservations",
                "Delivery Time",
                "Delegates",
                "Market Research",
                "Helpful Contacts",
            ],
            "level9": [
                "Flight Information",
                "Company Strategy",
                "A Difficult Visitor",
                "A Busy Thursday Morning",
                "A Defective Product",
                "Service Problems",
                "Project Risks",
                "A Green Challenge",
                "Sales Training Analysis",
            ],
            "level10": [
                "Trouble with Orders",
                "Organizing a Stay",
                "Welcoming Visitors",
                "The Company Stand",
                "Comparing Products",
                "Making a Sale",
                "Negotiating ",
                "Handling an Invoice",
                "A Disagreement",
            ],
            "level11": [
                "An Advertising Strategy",
                "Technical Explanations",
                "A Salary Negotiation",
                "Legal Advice",
                "The Project",
                "Client Negotiations",
                "The Economy",
                "The Press Article",
                "Department Restructure",
            ],
            "level12": [
                "The Decision ",
                "Product Analysis ",
                "Information Security ",
                "Artistic Differences",
                "A Convincing Argument ",
                "End-of-Year Summary  ",
                "A Recruitment Strategy  ",
                "Sales Training Negotiation",
                "Customer Service Objectives",
            ]
        }
    };

    var shClassSystem = angular.module('shClassSystem', []);
    shClassSystem.controller("shClassSystemCtrl", function($scope, $http) {
        $scope.genLecture = classSystemData.generalLecture;
        $scope.genOto = classSystemData.generalOto;
        $scope.busLecture = classSystemData.businessLecture;
        $scope.busOto = classSystemData.businessOto;
    });
    angular.bootstrap($("#shClassSystem"), ["shClassSystem"]);
});
