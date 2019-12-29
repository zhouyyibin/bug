(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-313c900e"],{"30e9":function(e,t,a){"use strict";a.r(t);var r,n=function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",[a("a-row",{attrs:{gutter:24}},[a("a-col",{attrs:{span:4}},[a("a-card",{attrs:{bordered:!1,title:"部门结构"}},[a("a-tree",{attrs:{defaultSelectedKeys:[0],ddefaultExpandParent:"",showLine:"",treeData:e.treeData},on:{select:e.onSelectDepartment}})],1)],1),a("a-col",{attrs:{span:20}},[a("a-card",{attrs:{bordered:!1}},[a("div",{attrs:{slot:"title"},slot:"title"},[a("a-input-search",{staticStyle:{"margin-left":"10px",width:"200px"},attrs:{placeholder:"搜索关键词"},on:{search:e.onSearch},model:{value:e.queryParam.keyword,callback:function(t){e.$set(e.queryParam,"keyword",t)},expression:"queryParam.keyword"}}),a("div",{staticStyle:{display:"inline-block","margin-right":"10px","font-size":"14px",float:"right"}},[a("a-button",{directives:[{name:"action",rawName:"v-action",value:"org_user_add",expression:"`org_user_add`"}],attrs:{type:"primary"},on:{click:function(t){return e.$refs.modal.add()}}},[a("a-icon",{attrs:{type:"plus"}}),e._v("添加用户\n            ")],1)],1)],1),a("s-table",{ref:"table",attrs:{size:"default",rowKey:"id",columns:e.columns,data:e.loadData},scopedSlots:e._u([{key:"action",fn:function(t,r){return a("span",{},[a("a",{directives:[{name:"action",rawName:"v-action",value:"org_user_edit",expression:"`org_user_edit`"}],on:{click:function(t){return e.$refs.modal.edit(r)}}},[e._v("编辑")]),a("a-divider",{attrs:{type:"vertical"}}),a("a",{directives:[{name:"action",rawName:"v-action",value:"org_user_resetpwd",expression:"`org_user_resetpwd`"}],on:{click:function(t){return e.handleResetPwd(r.id)}}},[e._v("重置密码")]),a("a-divider",{attrs:{type:"vertical"}}),a("a",{directives:[{name:"action",rawName:"v-action",value:"org_user_delete",expression:"`org_user_delete`"}],attrs:{href:"javascript:;"},on:{click:function(t){return e.handleDelete(r)}}},[e._v(e._s(r.status?"禁用":"启用"))]),a("a-divider",{attrs:{type:"vertical"}}),a("a",{directives:[{name:"action",rawName:"v-action",value:"org_user_remove",expression:"`org_user_remove`"}],attrs:{href:"javascript:;"},on:{click:function(t){return e.handleRemove(r)}}},[e._v("删除")])],1)}}])})],1)],1)],1),a("user-modal",{ref:"modal",attrs:{"role-list":e.roleList,"department-list":e.departmentList},on:{ok:e.handleOk}})],1)},o=[],l=a("bd86"),i=a("5176"),s=a.n(i),c=(a("6762"),a("2fdb"),a("7f7f"),a("2af9")),u=a("365c"),d=function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("a-modal",{staticStyle:{top:"20px"},attrs:{title:"操作",width:800},on:{ok:e.handleOk},model:{value:e.visible,callback:function(t){e.visible=t},expression:"visible"}},[a("a-form",{attrs:{form:e.form,id:"userForm"}},[a("a-form-item",{attrs:{labelCol:e.labelCol,wrapperCol:e.wrapperCol,label:"所属部门"}},[a("a-select",{directives:[{name:"decorator",rawName:"v-decorator",value:["departmentId",{initialValue:"0",rules:[{required:!0,message:"请选择部门"}]}],expression:"[\n          'departmentId',\n          {\n            initialValue: '0',\n            rules: [{ required: true, message: '请选择部门' }]\n          }\n        ]"}],on:{change:e.handleChange}},[a("a-select-option",{attrs:{value:"0"}},[e._v("/")]),e._l(e.departmentList,function(t){return[a("a-select-option",{attrs:{value:t.id}},[e._v(e._s(t.name))]),e._l(t.departments,function(r){return[a("a-select-option",{attrs:{value:r.id}},[e._v(e._s(t.name)+">"+e._s(r.name))]),e._l(r.departments,function(n){return[a("a-select-option",{attrs:{value:n.id}},[e._v(e._s(t.name)+">"+e._s(r.name)+">"+e._s(n.name))])]})]})]})],2)],1),a("a-form-item",{attrs:{labelCol:e.labelCol,wrapperCol:e.wrapperCol,label:"账号"}},[a("a-input",{directives:[{name:"decorator",rawName:"v-decorator",value:["account",{rules:[{required:!0,message:"请输入邮箱格式的账号"},{type:"email",message:"请输入正确邮箱格式"}]}],expression:"[\n          'account',\n          {rules: [{ required: true, message: '请输入邮箱格式的账号' }, {type: 'email', message: '请输入正确邮箱格式'}]}\n        ]"}],attrs:{placeholder:""}})],1),a("a-form-item",{attrs:{labelCol:e.labelCol,wrapperCol:e.wrapperCol,label:"真实姓名"}},[a("a-input",{directives:[{name:"decorator",rawName:"v-decorator",value:["name",{rules:[{required:!0,message:"请输入真实姓名"}]}],expression:"[\n          'name',\n          {rules: [{ required: true, message: '请输入真实姓名' }]}\n        ]"}],attrs:{placeholder:""}})],1),a("a-form-item",{attrs:{labelCol:e.labelCol,wrapperCol:e.wrapperCol,label:"角色"}},[a("a-select",{directives:[{name:"decorator",rawName:"v-decorator",value:["roleBeans",{rules:[{required:!0,message:"请选择用户角色"}]}],expression:"[\n          'roleBeans',\n          {rules: [{ required: true, message: '请选择用户角色' }]}\n        ]"}],attrs:{placeholder:"",mode:"multiple"}},e._l(e.roleList,function(t){return a("a-select-option",{key:t.id,attrs:{value:t.id}},[e._v(e._s(t.name))])}),1)],1),a("a-form-item",{attrs:{labelCol:e.labelCol,wrapperCol:e.wrapperCol,label:"职位"}},[a("a-input",{directives:[{name:"decorator",rawName:"v-decorator",value:["position"],expression:"[\n          'position'\n        ]"}],attrs:{placeholder:""}})],1),a("a-form-item",{attrs:{labelCol:e.labelCol,wrapperCol:e.wrapperCol,label:"联系电话"}},[a("a-input",{directives:[{name:"decorator",rawName:"v-decorator",value:["tel"],expression:"[\n          'tel'\n        ]"}],attrs:{placeholder:""}})],1),a("a-form-item",{attrs:{labelCol:e.labelCol,wrapperCol:e.wrapperCol,label:"QQ"}},[a("a-input",{directives:[{name:"decorator",rawName:"v-decorator",value:["qq"],expression:"[\n          'qq'\n        ]"}],attrs:{placeholder:""}})],1),a("a-form-item",{attrs:{labelCol:e.labelCol,wrapperCol:e.wrapperCol,label:"微信"}},[a("a-input",{directives:[{name:"decorator",rawName:"v-decorator",value:["wechar"],expression:"[\n          'wechar'\n        ]"}],attrs:{placeholder:""}})],1),a("a-form-item",{attrs:{labelCol:e.labelCol,wrapperCol:e.wrapperCol,label:"性别"}},[a("a-radio-group",{directives:[{name:"decorator",rawName:"v-decorator",value:["sex",{initialValue:1}],expression:"[\n          'sex',\n          {\n            initialValue: 1,\n          }\n        ]"}],attrs:{name:"radioGroup"}},[a("a-radio",{attrs:{value:1}},[e._v("男")]),a("a-radio",{attrs:{value:0}},[e._v("女")]),a("a-radio",{attrs:{value:2}},[e._v("未知")])],1)],1),a("a-form-item",{attrs:{labelCol:e.labelCol,wrapperCol:e.wrapperCol,label:"数据权限"}},[a("a-radio-group",{directives:[{name:"decorator",rawName:"v-decorator",value:["setData",{initialValue:1}],expression:"[\n          'setData',\n          {\n            initialValue: 1,\n          }\n        ]"}],attrs:{name:"radioGroup"}},[a("a-radio",{attrs:{value:3}},[e._v("本人数据")]),a("a-radio",{attrs:{value:2}},[e._v("本部门数据")]),a("a-radio",{attrs:{value:1}},[e._v("本公司数据")])],1)],1)],1)],1)},m=[],p=a("88bc"),f=a.n(p),v={name:"UserModal",props:["departmentList","roleList"],data:function(){return{labelCol:{xs:{span:24},sm:{span:5}},wrapperCol:{xs:{span:24},sm:{span:16}},visible:!1,confirmLoading:!1,mdl:{},form:this.$form.createForm(this)}},methods:{handleCheckAccount:function(e,t,a){/^[0-9a-zA-Z]+$/.test(t)?a():a(new Error("请输入字母或数字组合的账号"))},add:function(){var e=this;this.mdl=null,this.visible=!0,this.$nextTick(function(){e.form.resetFields()})},edit:function(e){var t=this;this.mdl=e?s()({},e):"",this.visible=!0,console.log(this.mdl),this.$nextTick(function(){t.form.setFieldsValue(f()(t.mdl,"name","account","position","departmentId","qq","wechar","mail","roleBeans","setData"))})},close:function(){this.$emit("close"),this.visible=!1},handleOk:function(){var e=this;this.form.validateFields(function(t,a){if(!t){e.confirmLoading=!0;var r=e.mdl?"update":"save";e.mdl&&(a.id=e.mdl.id),u["default"].user[r](a).then(function(t){e.$message.success("保存成功"),e.$emit("ok")}).catch(function(){}).finally(function(){e.confirmLoading=!1,e.close()})}})},handleCancel:function(){this.close()},handleChange:function(e){console.log(e)}}},h=v,b=a("2877"),w=Object(b["a"])(h,d,m,!1,null,"40a86cb3",null),g=w.exports,C=a("c1df"),_=a.n(C),y={name:"TableList",components:{STable:c["f"],UserModal:g},data:function(){var e=this;return{treeData:[],visible:!1,labelCol:{xs:{span:24},sm:{span:5}},wrapperCol:{xs:{span:24},sm:{span:16}},form:null,mdl:{},advanced:!1,columns:[{title:"真实姓名",dataIndex:"name"},{title:"登录账号",dataIndex:"account"},{title:"职位",dataIndex:"position"},{title:"部门",dataIndex:"department",customRender:function(e){return e.name}},{title:"最后登录",dataIndex:"lastLoginTime",customRender:function(e){return e&&_()(new Date(e)).format("YYYY/MM/DD HH:mm")}},{title:"角色",dataIndex:"roleBeans",customRender:function(t){var a=e.roleList.filter(function(e){return t.includes(e.id)}),r=a.map(function(e){return e.name});return r.join(",")}},{title:"操作",width:"200px",dataIndex:"action",scopedSlots:{customRender:"action"}}],departmentList:[],roleList:[],queryParam:{keyword:"",department_id:null},loadData:function(t){return u["default"].user.list(s()(t,e.queryParam)).then(function(e){return e.result})},selectedRowKeys:[],selectedRows:[]}},created:function(){this.getDepartList(),this.getRoleList()},methods:(r={onSearch:function(){this.$refs.table.refresh()},getRoleList:function(){var e=this;u["default"].role.list({pageNo:1,pageSize:-1}).then(function(t){var a=t.result.data;e.roleList=a})},getDepartList:function(){var e=this;u["default"].department.tree().then(function(t){var a=t.result.data,r=[{key:0,name:"艾鑫集团",id:0,departments:a}];e.departmentList=e.formatTree(a),e.treeData=e.formatTree(r)})},formatTree:function(e){var t=this;return e.map(function(e){return e.title=e.name,e.departments&&e.departments.length>0&&(e.children=t.formatTree(e.departments)),e})},handleDelete:function(e){var t=this;this.$confirm({title:"温馨提示",content:e.status?"您确定要禁用该用户吗？禁用后用户无法登录":"您确定要启用该用户吗？启用后该用户能正常登陆",okText:"确定",okType:"danger",cancelText:"取消",onOk:function(){u["default"].user.remove(e.id).then(function(){return t.$refs.table.refresh(!1)})},onCancel:function(){console.log("Cancel")}})},handleRemove:function(e){var t=this;this.$confirm({title:"温馨提示",content:"您确定要删除该用户吗？删除后将无法恢复",okText:"确定",okType:"danger",cancelText:"取消",onOk:function(){u["default"].user.deleteUser(e.id).then(function(){return t.$refs.table.refresh(!1)})},onCancel:function(){console.log("Cancel")}})}},Object(l["a"])(r,"handleRemove",function(e){var t=this;this.$confirm({title:"温馨提示",content:"您确定要删除该用户吗？删除用户后无法恢复",okText:"确定",okType:"danger",cancelText:"取消",onOk:function(){u["default"].user.deleteUser(e.id).then(function(){return t.$refs.table.refresh(!0)})},onCancel:function(){console.log("Cancel")}})}),Object(l["a"])(r,"handleEdit",function(e){this.$refs.modal.visible=!0}),Object(l["a"])(r,"handleOk",function(){this.$refs.table.refresh(!1)}),Object(l["a"])(r,"handleResetPwd",function(e){this.$confirm({title:"温馨提示",content:"您确定要重置该用户密码吗？",okText:"确定",okType:"danger",cancelText:"取消",onOk:function(){u["default"].user.resetPwd(e).then(function(e){console.log(e)})},onCancel:function(){console.log("Cancel")}})}),Object(l["a"])(r,"onSelectDepartment",function(e,t){var a=t.node.dataRef.id;this.queryParam.department_id=a,this.$refs.table.refresh()}),r),watch:{}},x=y,k=Object(b["a"])(x,n,o,!1,null,null,null);t["default"]=k.exports},"88bc":function(e,t,a){(function(t){var a=1/0,r=9007199254740991,n="[object Arguments]",o="[object Function]",l="[object GeneratorFunction]",i="[object Symbol]",s="object"==typeof t&&t&&t.Object===Object&&t,c="object"==typeof self&&self&&self.Object===Object&&self,u=s||c||Function("return this")();function d(e,t,a){switch(a.length){case 0:return e.call(t);case 1:return e.call(t,a[0]);case 2:return e.call(t,a[0],a[1]);case 3:return e.call(t,a[0],a[1],a[2])}return e.apply(t,a)}function m(e,t){var a=-1,r=e?e.length:0,n=Array(r);while(++a<r)n[a]=t(e[a],a,e);return n}function p(e,t){var a=-1,r=t.length,n=e.length;while(++a<r)e[n+a]=t[a];return e}var f=Object.prototype,v=f.hasOwnProperty,h=f.toString,b=u.Symbol,w=f.propertyIsEnumerable,g=b?b.isConcatSpreadable:void 0,C=Math.max;function _(e,t,a,r,n){var o=-1,l=e.length;a||(a=j),n||(n=[]);while(++o<l){var i=e[o];t>0&&a(i)?t>1?_(i,t-1,a,r,n):p(n,i):r||(n[n.length]=i)}return n}function y(e,t){return e=Object(e),x(e,t,function(t,a){return a in e})}function x(e,t,a){var r=-1,n=t.length,o={};while(++r<n){var l=t[r],i=e[l];a(i,l)&&(o[l]=i)}return o}function k(e,t){return t=C(void 0===t?e.length-1:t,0),function(){var a=arguments,r=-1,n=C(a.length-t,0),o=Array(n);while(++r<n)o[r]=a[t+r];r=-1;var l=Array(t+1);while(++r<t)l[r]=a[r];return l[t]=o,d(e,this,l)}}function j(e){return L(e)||O(e)||!!(g&&e&&e[g])}function $(e){if("string"==typeof e||I(e))return e;var t=e+"";return"0"==t&&1/e==-a?"-0":t}function O(e){return q(e)&&v.call(e,"callee")&&(!w.call(e,"callee")||h.call(e)==n)}var L=Array.isArray;function T(e){return null!=e&&N(e.length)&&!D(e)}function q(e){return R(e)&&T(e)}function D(e){var t=S(e)?h.call(e):"";return t==o||t==l}function N(e){return"number"==typeof e&&e>-1&&e%1==0&&e<=r}function S(e){var t=typeof e;return!!e&&("object"==t||"function"==t)}function R(e){return!!e&&"object"==typeof e}function I(e){return"symbol"==typeof e||R(e)&&h.call(e)==i}var P=k(function(e,t){return null==e?{}:y(e,m(_(t,1),$))});e.exports=P}).call(this,a("c8ba"))}}]);