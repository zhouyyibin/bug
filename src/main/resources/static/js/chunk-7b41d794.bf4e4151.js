(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-7b41d794"],{"17f4":function(e,t,n){"use strict";n.r(t);var r=function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("a-card",{attrs:{bordered:!1}},[n("div",{directives:[{name:"action",rawName:"v-action",value:"org_role_add",expression:"`org_role_add`"}],staticClass:"table-page-search-wrapper",staticStyle:{"text-align":"right","margin-bottom":"10px"}},[n("a-button",{attrs:{type:"primary"},on:{click:function(t){return e.$refs.modal.add()}}},[n("a-icon",{attrs:{type:"plus"}}),e._v("添加角色\n    ")],1)],1),n("s-table",{ref:"table",attrs:{rowKey:"id",size:"default",columns:e.columns,data:e.loadData},scopedSlots:e._u([{key:"action",fn:function(t,r){return n("span",{},[n("a",{directives:[{name:"action",rawName:"v-action",value:"org_role_edit",expression:"`org_role_edit`"}],on:{click:function(t){return e.$refs.modal.edit(r)}}},[e._v("编辑")]),n("a-divider",{attrs:{type:"vertical"}}),n("a",{directives:[{name:"action",rawName:"v-action",value:"org_role_permission",expression:"`org_role_permission`"}],attrs:{href:"javascript:;"},on:{click:function(t){return e.$router.push("/org/permission/"+r.id+"?title="+r.name)}}},[e._v("权限管理")]),n("a-divider",{attrs:{type:"vertical"}}),n("a",{directives:[{name:"action",rawName:"v-action",value:"org_role_delete",expression:"`org_role_delete`"}],on:{click:function(t){return e.handleDelete(r.id)}}},[e._v("删除")])],1)}}])}),n("role-modal",{ref:"modal",on:{ok:e.handleOk}})],1)},a=[],i=n("5176"),o=n.n(i),l=n("2af9"),c=function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("a-modal",{staticStyle:{top:"20px"},attrs:{title:(this.mdl?"编辑":"新增")+"角色",width:800},on:{ok:e.handleOk},model:{value:e.visible,callback:function(t){e.visible=t},expression:"visible"}},[n("a-form",{attrs:{form:e.form,id:"userForm"}},[n("a-form-item",{attrs:{labelCol:e.labelCol,wrapperCol:e.wrapperCol,label:"角色名称",hasFeedback:""}},[n("a-input",{directives:[{name:"decorator",rawName:"v-decorator",value:["name",{rules:[{required:!0,message:"请输入角色名称"}]}],expression:"[\n          'name',\n          {rules: [{ required: true, message: '请输入角色名称' }]}\n        ]"}],attrs:{placeholder:"请输入角色名称"}})],1),n("a-form-item",{attrs:{labelCol:e.labelCol,wrapperCol:e.wrapperCol,label:"角色描述",hasFeedback:""}},[n("a-textarea",{directives:[{name:"decorator",rawName:"v-decorator",value:["describe"],expression:"[\n          'describe'\n        ]"}],attrs:{placeholder:"请输入角色描述"}})],1)],1)],1)},s=[],u=n("365c"),d=n("88bc"),f=n.n(d),h={name:"DepartModal",data:function(){return{labelCol:{xs:{span:24},sm:{span:5}},wrapperCol:{xs:{span:24},sm:{span:16}},visible:!1,confirmLoading:!1,mdl:{},data:[],value:"",form:this.$form.createForm(this)}},methods:{handleCheckAccount:function(e,t,n){/^[0-9a-zA-Z]+$/.test(t)?n():n(new Error("请输入字母或数字组合的账号"))},add:function(){var e=this;this.mdl=null,this.visible=!0,this.$nextTick(function(){e.form.resetFields()})},edit:function(e){var t=this;this.mdl=e?o()({},e):"",this.visible=!0,this.$nextTick(function(){t.form.setFieldsValue(f()(t.mdl,"describe","name"))})},close:function(){this.$emit("close"),this.visible=!1},handleOk:function(){var e=this;this.form.validateFields(function(t,n){if(!t){e.confirmLoading=!0;var r=e.mdl?"update":"save";e.mdl&&(n.id=e.mdl.id),u["default"].role[r](n).then(function(t){e.$message.success("保存成功"),e.$emit("ok")}).catch(function(){}).finally(function(){e.confirmLoading=!1,e.close()})}})},handleCancel:function(){this.close()},onChangeCheck:function(e){e.indeterminate=!!e.selected.length&&e.selected.length<e.actionsOptions.length,e.checkedAll=e.selected.length===e.actionsOptions.length},onChangeCheckAll:function(e,t){o()(t,{selected:e.target.checked?t.actionsOptions.map(function(e){return e.value}):[],indeterminate:!1,checkedAll:e.target.checked})},handleChange:function(e){console.log(e)}}},m=h,p=n("2877"),v=Object(p["a"])(m,c,s,!1,null,"16533fce",null),b=v.exports,g={name:"TableList",components:{STable:l["f"],RoleModal:b},data:function(){var e=this;return{description:"列表使用场景：后台管理中的权限管理以及角色管理，可用于基于 RBAC 设计的角色权限控制，颗粒度细到每一个操作类型。",visible:!1,form:null,mdl:{},advanced:!1,queryParam:{},columns:[{title:"角色名称",dataIndex:"name"},{title:"角色描述",dataIndex:"describe"},{title:"操作",width:"250px",dataIndex:"action",scopedSlots:{customRender:"action"}}],loadData:function(t){return u["default"].role.list(o()(t,e.queryParam)).then(function(e){return e.result})},selectedRowKeys:[],selectedRows:[],permissionList:[]}},created:function(){this.getPermissions()},methods:{getPermissions:function(){var e=this;u["default"].permission.list().then(function(t){e.permissionList=t.result.data})},handleEdit:function(e){this.$ref.modal.visible=!0},handleOk:function(){this.$refs.table.refresh()},handleOkPermission:function(){},handleDelete:function(e){var t=this;this.$confirm({title:"温馨提示",content:"您确定要删除该角色吗？",okText:"确定",okType:"danger",cancelText:"取消",onOk:function(){u["default"].role.remove(e).then(function(){return t.$refs.table.refresh(!1)})},onCancel:function(){console.log("Cancel")}})}},watch:{}},w=g,y=Object(p["a"])(w,r,a,!1,null,null,null);t["default"]=y.exports},"88bc":function(e,t,n){(function(t){var n=1/0,r=9007199254740991,a="[object Arguments]",i="[object Function]",o="[object GeneratorFunction]",l="[object Symbol]",c="object"==typeof t&&t&&t.Object===Object&&t,s="object"==typeof self&&self&&self.Object===Object&&self,u=c||s||Function("return this")();function d(e,t,n){switch(n.length){case 0:return e.call(t);case 1:return e.call(t,n[0]);case 2:return e.call(t,n[0],n[1]);case 3:return e.call(t,n[0],n[1],n[2])}return e.apply(t,n)}function f(e,t){var n=-1,r=e?e.length:0,a=Array(r);while(++n<r)a[n]=t(e[n],n,e);return a}function h(e,t){var n=-1,r=t.length,a=e.length;while(++n<r)e[a+n]=t[n];return e}var m=Object.prototype,p=m.hasOwnProperty,v=m.toString,b=u.Symbol,g=m.propertyIsEnumerable,w=b?b.isConcatSpreadable:void 0,y=Math.max;function k(e,t,n,r,a){var i=-1,o=e.length;n||(n=O),a||(a=[]);while(++i<o){var l=e[i];t>0&&n(l)?t>1?k(l,t-1,n,r,a):h(a,l):r||(a[a.length]=l)}return a}function x(e,t){return e=Object(e),_(e,t,function(t,n){return n in e})}function _(e,t,n){var r=-1,a=t.length,i={};while(++r<a){var o=t[r],l=e[o];n(l,o)&&(i[o]=l)}return i}function C(e,t){return t=y(void 0===t?e.length-1:t,0),function(){var n=arguments,r=-1,a=y(n.length-t,0),i=Array(a);while(++r<a)i[r]=n[t+r];r=-1;var o=Array(t+1);while(++r<t)o[r]=n[r];return o[t]=i,d(e,this,o)}}function O(e){return A(e)||$(e)||!!(w&&e&&e[w])}function j(e){if("string"==typeof e||D(e))return e;var t=e+"";return"0"==t&&1/e==-n?"-0":t}function $(e){return S(e)&&p.call(e,"callee")&&(!g.call(e,"callee")||v.call(e)==a)}var A=Array.isArray;function F(e){return null!=e&&L(e.length)&&!T(e)}function S(e){return P(e)&&F(e)}function T(e){var t=N(e)?v.call(e):"";return t==i||t==o}function L(e){return"number"==typeof e&&e>-1&&e%1==0&&e<=r}function N(e){var t=typeof e;return!!e&&("object"==t||"function"==t)}function P(e){return!!e&&"object"==typeof e}function D(e){return"symbol"==typeof e||P(e)&&v.call(e)==l}var E=C(function(e,t){return null==e?{}:x(e,f(k(t,1),j))});e.exports=E}).call(this,n("c8ba"))}}]);