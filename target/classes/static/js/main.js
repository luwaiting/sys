$(".navbar .nav-link").click(function () {
    console.log(this);
    const target = $(this).attr("href");
    console.log("移動目標", target);
    const position = $(target).offset().top;
    console.log("座標", position);
    // $("html,body").animate({
    //     scrollTop: position
    // }, 1000);
});