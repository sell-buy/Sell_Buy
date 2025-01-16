$(function () {
    $.get("categories")
        .done(function (data) {
            let categoryNav = $('.category');
            categoryNav.empty(); // Clear any existing content

            // Sort categories alphabetically by name
            data.sort(function (a, b) {
                return a.name.localeCompare(b.name);
            });

            data.forEach(function (category) {
                let categoryItem = $('<div>').addClass('category-item');
                let categoryLink = $('<a>').attr('href', 'prod/list?catId=' + category.catId).text(category.name);
                categoryItem.append(categoryLink);
                categoryNav.append(categoryItem);
            });
        })
        .fail(function (jqXHR, textStatus, errorThrown) {
            console.error("Error fetching categories:", textStatus, errorThrown);
        });
});