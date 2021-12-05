let storage = window.localStorage;

function getImageCaptcha(){
    axios.get('/captcha', {
        params: {captchaType: 'LETTER_IMAGE'}
    }).then(function (response) {
        const resp = response.data;
        if (resp.code === "1000"){
            const img = document.getElementById("image-captcha");
            const data = response.data.data;
            console.log(data)
            img.setAttribute("src", data.mainImage);
            storage.setItem("cliToken", data.cliToken);
            return;
        }
        alert(resp.message);
    }).catch(function (error) {
        alert("resource error!" + error);
    });
}



function getRotateCaptcha(){
    axios.get('/captcha', {
        params: {captchaType: 'ROTATE_PICTURE'}
    }).then(function (response) {
        const resp = response.data;
        if (resp.code === "1000"){
            const img = document.getElementById("rotate-captcha");
            const data = response.data.data;
            console.log(data)
            img.setAttribute("src", data.mainImage);
            storage.setItem("cliToken", data.cliToken);
            return;
        }
        alert(resp.message);
    }).catch(function (error) {
        alert("resource error!" + error);
    });
}

function getSlideCaptcha(){
    axios.get('/captcha', {
        params: {captchaType: 'SLIDE_BLOCK'}
    }).then(function (response) {
        const resp = response.data;
        if (resp.code === "1000"){
            const main = document.getElementById("slide-captcha-main");
            const sub = document.getElementById("slide-captcha-sub");
            const data = response.data.data;
            console.log(data)
            main.setAttribute("src", data.mainImage);
            sub.setAttribute("src", data.slideImage);
            storage.setItem("cliToken", data.cliToken);
            return;
        }
        alert(resp.message);
    }).catch(function (error) {
        alert("resource error!" + error);
    });
}

function getClickCaptcha(){
    axios.get('/captcha', {
        params: {captchaType: 'CLICK_PATTERN'}
    }).then(function (response) {
        const resp = response.data;
        if (resp.code === "1000"){
            const main = document.getElementById("click-captcha-main");
            const sub = document.getElementById("click-captcha-sub");
            const data = response.data.data;
            console.log(data)
            main.setAttribute("src", data.mainImage);
            sub.setAttribute("src", data.patternImage);
            storage.setItem("cliToken", data.cliToken);
            return;
        }
        alert(resp.message);
    }).catch(function (error) {
        alert("resource error!" + error);
    });
}

function checkCaptcha(){
    const input = document.getElementById("image-captcha-content");
    const param = {
        "cliToken" : storage.getItem("cliToken"),
        "trackInfo" : input.value
    };
    axios.post('/captcha', param).then(function (response) {
        const resp = response.data;
        if (resp.code === "1000"){
            getResource(resp.data.accessToken)
            return;
        }
        alert("wrong input, now refresh captcha.");
        getCaptcha("LETTER_IMAGE")
    }).catch(function (error) {
        console.log(error);
    });
}

function getResource(accessToken){
    axios.get('/resource', {
        params: {accessToken: accessToken}
    }).then(function (response) {
        const resp = response.data;
        if (resp.code === "1000"){
            alert("resource access successful!");
            return;
        }
        alert("resource access fail!");
    }).catch(function (error) {
        alert("resource error!" + error);
    });
}

window.onload=function(){
    getClickCaptcha();
    getSlideCaptcha();
    getRotateCaptcha();
}





