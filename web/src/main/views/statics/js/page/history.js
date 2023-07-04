$(function () {
    var image = document.getElementById("image_viewer_id");
    new Viewer(image , {
        url:"data-original"
    });
});