let page = {
    urls: {
        getAllProducts: AppBase.BASE_URL_PRODUCT,
        doSave: AppBase.BASE_URL_PRODUCT,
        doDelete: App.BASE_URL_PRODUCT + "/delete/"
    },
    element: {},
    commands: {},
    loadData: {},
    dialogs: {
        elements: {},
        commands: {}
    }
}

page.element.loader = $(".loader");
page.element.tempProduct = $("#tempProduct");
page.element.currentRow = $("#currentRow");
page.element.btnShowCreateModal = $("a.create-modal");

page.element.btnDelete = $("#tempProduct body");
page.element.btnSaveCre = $("#btnSaveCre");

page.element.frmListProduct = $("#tbListProducts tbody");

page.dialogs.elements.modalCreateProduct = $("#modalCreateProduct");
page.dialogs.elements.frmProductCre = $("#frmProductCre");

page.dialogs.elements.wrapper = $("section .wrapper");
page.dialogs.elements.productTitleCre = $("#productTitleCre");
page.dialogs.elements.productPriceCre = $("#productPriceCre");
page.dialogs.elements.productQuantityCre = $("#productQuantityCre")
page.dialogs.elements.descriptionCre = $("#descriptionCre");
page.dialogs.elements.imageFileCre = $("#imageFileCre");
page.dialogs.elements.wrapperContent = $("section .wrapper .content");
page.dialogs.elements.imagePreview = $("section .image-preview canvas");
page.dialogs.elements.canvasCre = $("#canvasCre");
page.dialogs.elements.context = page.dialogs.elements.canvas[0].getContext('2d');
page.dialogs.elements.imagePreview.css("display", "none");
page.dialogs.elements.divImagePreview = $("div.image-preview, div.file-name");
page.dialogs.elements.btnClearImagePreview = $(".clear-image-preview i");

page.dialogs.elements.modalUpdateProduct = $("#modalUpdateProduct");
page.dialogs.elements.frmProductUp = $("#frmProductUp");


page.dialogs.elements.productTitleUp = $("#productTitleUp");
page.dialogs.elements.productPriceUp = $("#productPriceUp");
page.dialogs.elements.productQuantityUp = $("#productQuantityUp")
page.dialogs.elements.descriptionUp = $("#descriptionUp");
page.dialogs.elements.imageFileUp = $("#imageFileUp");
page.dialogs.elements.canvasUp = $("#canvasUp");


page.loadData.getAllProduct = () =>{
    $.ajax({
        type:"GET",
        url: page.urls.getAllProducts
    })
        .done((data) =>{
            $.each(data, (i,item) =>{

            })
    })
        .fail((error) =>{
            console.log(error)
        })
        .always(function () {
        page.element.loader.addClass("hide");
        page.element.btnSaveCre.prop('disabled', false);
    });
}

page.commands.showCreateModal = () => {
    page.dialogs.elements.modalCreateProduct.modal('show');
}

page.dialogs.commands.loadImageToCanvas = (imageFile) => {
    page.dialogs.elements.imagePreview.css("display", "");
    page.dialogs.elements.wrapper.addClass("active");
    page.dialogs.elements.wrapperContent.css("opacity", 0);

    let imageObj = new Image();

    imageObj.onload = function () {
        page.dialogs.elements.context.canvas.width = imageObj.width;
        page.dialogs.elements.context.canvas.height = imageObj.height;
        page.dialogs.elements.context.drawImage(imageObj, 0, 0, imageObj.width, imageObj.height);
    };

    imageObj.src = URL.createObjectURL(imageFile)
}

page.dialogs.commands.changeImagePreview = () => {
    let imageFile = page.dialogs.elements.imageFile[0].files[0];

    if (imageFile) {
        let reader = new FileReader();

        reader.readAsDataURL(imageFile);

        reader.onload = function(e){
            if (e.target.readyState === FileReader.DONE) {
                page.dialogs.commands.loadImageToCanvas(imageFile);
            }
        }
    } else {
        page.dialogs.elements.clearImagePreview();
    }
}

page.dialogs.commands.createProduct = () => {
    page.element.loader.removeClass("hide");
    page.element.btnSaveCre.prop('disabled', true);

    let formData = new FormData();
    formData.append("title", page.dialogs.elements.productTitleCre.val().toString());
    formData.append("price", page.dialogs.elements.productPriceCre.val().toString());
    formData.append("quantity",page.dialogs.elements.productQuantityCre.val().toString());
    formData.append("description", page.dialogs.elements.descriptionCre.val().toString());
    formData.append("file", page.dialogs.elements.imageFile[0].files[0]);

    $.ajax({
        type: "POST",
        contentType: false,
        cache: false,
        processData: false,
        url: page.urls.doSave,
        data: formData
    }).done((data) => {
        console.log(data)
        page.commands.renderProduct(data);

        // page.commands.addRow();

        AppBase.SweetAlert.showSuccessAlert("Thêm sản phẩm thành công");

        page.dialogs.elements.modalCreateProduct.modal('hide');

    }).fail((err) => {
        console.log(err)
        AppBase.SweetAlert.showErrorAlert("Thêm sản phẩm thất bại");
    }).always(function () {
        page.element.loader.addClass("hide");
        page.element.btnSaveCre.prop('disabled', false);
    });
}

page.commands.renderProduct = (obj) => {

    let cloudinaryServer = 'https://res.cloudinary.com/dkomrvd0q/image/upload';
    let BASE_SCALE_IMAGE = "c_limit,w_150,h_100,q_100";
    let folderName = obj.folderName;
    let fileName = obj.fileName;

    let avatarUrl = cloudinaryServer + '/' + BASE_SCALE_IMAGE + '/' + folderName + '/' + fileName;

    let str = `
                <tr>
                    <td>${obj.id}</td>
                    <td>
                        <img src="${avatarUrl}" alt=""/>
                    </td>
                    <td>${obj.title}</td>
                    <td>${obj.price}</td>
                    <td>${obj.quantity}</td>
                    <td>${obj.description}</td>
                    <td>Edit</td>
                    <td>Delete</td>
                </tr>
            `;

    page.element.frmListProduct.prepend(str);

    page.initializeControlEvent = () => {

        page.element.btnShowCreateModal.on("click", function () {
            page.commands.showCreateModal();
        });

        page.element.btnSaveCre.on("click", function () {
            page.dialogs.commands.createProduct();
        });+-

        page.dialogs.elements.divImagePreview.on("click", function () {
            page.dialogs.elements.imageFile.trigger("click");
        });

        page.dialogs.elements.imageFile.on("change", function () {
            page.dialogs.commands.changeImagePreview();
        });

        // page.dialogs.elements.btnClearImagePreview.on("click", function () {
        //     page.dialogs.elements.clearImagePreview();
        // });

    }

    $(() => {
        page.loadData.getAllProducts();
        // page.loadData.getAllProducts().then(function () {
        //     page.initializeControlEvent();
        // });

        page.initializeControlEvent();

    });
}