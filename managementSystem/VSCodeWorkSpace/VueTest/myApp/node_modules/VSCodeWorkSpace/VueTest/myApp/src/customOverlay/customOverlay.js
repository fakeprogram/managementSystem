
export function LockAvatarLay(point, headerImg, { width = 50, height = 80 }, userId,userName,departmentName,address) {
    this._point = point
    this._headerImg = headerImg
    this._width = width
    this._height = height
    this._userName=userName
    this._UID = userId
    this._departmentName=departmentName
    this._address=address
}

//继承Overlay API
LockAvatarLay.prototype = new BMapGL.Overlay();
// 实现初始化自定义覆盖物方法
LockAvatarLay.prototype.initialize = function (map) {
    this._map = map;
    var divBox = document.createElement("div");
    var divImg = document.createElement("div");
    var divName = document.createElement("div");
    var divCont = document.createElement("div");
    divName.innerHTML = this._userName;
    divName.style.textAlign="center";
    divName.style.backgroundColor="white"
    divName.style.color="black";
    divCont.style.height="auto"
    divCont.style.width="auto"
    divCont.style.position="absolute"
    divCont.style.overflow="hidden"
    /*头像背景框*/
    divBox.style.position = "absolute";
    divBox.style.width = this._width + "px";
    divBox.style.height = this._height + "px";
    divBox.style.backgroundImage = "url(/headerbk.png)";
    divBox.style.backgroundSize = "100% 100%";
    /*头像CSS*/
    divImg.style.position = "absolute";
    divImg.style.backgroundImage = `url(${this._headerImg})`;
    divImg.style.left = "4%";
    divImg.style.top = "0%";
    divImg.style.width = "90%";
    divImg.style.height = "60%";
    divImg.style.borderRadius = "50%";
    divImg.style.backgroundSize = "cover";
    //divImg.style.border = "3px solid #08d5c0";//边框
    divCont.appendChild(divName)
    divCont.appendChild(divBox)
    divBox.appendChild(divImg);

    // 将div添加到覆盖物容器中
    map.getPanes().markerPane.appendChild(divCont);

    // 保存div实例
    this._div = divCont;
    // 需要将div元素作为方法的返回值，当调用该覆盖物的show
    // hide方法，或者对覆盖物进行移除时，API都将操作此元素。
    return divCont;
}

//实现绘制覆盖物方法
LockAvatarLay.prototype.draw = function () {
    var position = this._map.pointToOverlayPixel(this._point);
    this._div.style.left = position.x - this._width / 2 + "px";
    this._div.style.top = position.y - this._height * 7 / 10 + "px";
}

//实现隐藏和显示方法  
LockAvatarLay.prototype.hide = function () {
    if (this._div) {
        // this._div.style.display = "none";
        this._div.style.height = "0px";
        // this._div.style.width = "0px";
        this._div.style.transition = "height 0.3s";
    }
}

LockAvatarLay.prototype.show = function () {
    if (this._div) {
        // this._div.style.display = "";
        this._div.style.height = "100px";
        this._div.style.width = "50px";
        this._div.style.transition = "width 0.5s,height 0.5s";
    }
}

//实现事件监听方法
LockAvatarLay.prototype.addEventListener = function (event, fun) {
    this._div['on' + event] = fun;
}