/**
 * author ej date 2020-01-19
 */

var valida = {
    /* 判断是否为手机号 */
    isMobile : function(inVal) {
        var rex = /^1[3-9]+\d{9}$/;
        var b = false;
        if (rex.test(inVal)) {
            b = true;
        }
        return b;
    },
    /* 判断是否为座机号码 */
    isTel : function(inVal) {
        var rex = /^((0\d{2,3})-)?(\d{7,8})(-(\d{3,}))?$/;
        var b = false;
        if (rex.test(inVal)) {
            b = true;
        }
        return b;
    },
    /* 手机或座机 */
    isTelOrMobile : function(inVal) {
        var b = this.isMobile(inVal);
        if (b === true) {
            return b;
        } else {
            return this.isTel(inVal);
        }
    },
    /* 判断是否为邮箱 */
    isEmail : function(inVal) {
        var rex = /^([a-zA-Z]|[0-9])(\w|\-)+@[a-zA-Z0-9]+\.([a-zA-Z]{2,4})$/;
        var b = false;
        if (rex.test(inVal)) {
            b = true;
        }
        return b;
    }
    
    /* 初始化 哪些字段是必填的 */
    ,initWhoIsMandatory(str){
        try{
            var formatArr = str.split(",");
            for(var i in formatArr){
                fieldMandatory[colMapping[formatArr[i]]]['isMust'] = true
            }
        }catch(e){
            console.log("必填字段初始化失败")
        }
    }
}
