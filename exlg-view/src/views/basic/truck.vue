<template>
  <div>
    <!-- 面包屑导航 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>车辆管理</el-breadcrumb-item>
    </el-breadcrumb>
    <!-- 搜索筛选 -->
    <el-form :inline="true" class="user-search">
      <el-form-item label="搜索：">
        <el-input size="small" placeholder="输入车辆信息"></el-input>
      </el-form-item>
      <el-form-item label="">
        <el-input size="small" placeholder="输入车牌号"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button
          size="small"
          type="primary"
          icon="el-icon-search"
          @click="search"
          >搜索</el-button
        >
        <el-button
          size="small"
          type="primary"
          icon="el-icon-plus"
          @click="handleEdit()"
          >添加</el-button
        >
      </el-form-item>
    </el-form>
    <!--列表-->
    <el-table
      size="small"
      :data="currListData"
      highlight-current-row
      v-loading="loading"
      border
      element-loading-text="拼命加载中"
      style="width: 100%"
    >
      <el-table-column align="center" type="selection" width="60">
      </el-table-column>
      <el-table-column sortable prop="typeName" label="类型" width="200">
      </el-table-column>
      <el-table-column sortable prop="truckMark" label="车牌号" width="200">
      </el-table-column>
      <el-table-column sortable prop="status" label="状态" width="200">
      </el-table-column>
      <el-table-column
        sortable
        prop="maxWeight"
        label="最大承受重量(kg)"
        width="200"
      >
      </el-table-column>
      <el-table-column
        sortable
        prop="remainderWeight"
        label="剩余承受重量(kg)"
        width="200"
      >
      </el-table-column>
      <el-table-column align="center" label="操作" min-width="300">
        <template slot-scope="scope">
          <el-button size="mini" @click="sendCar(scope.$index, scope.row)" icon="el-icon-truck"
            >发车</el-button
          >
          <el-button
            size="mini"
            type="danger"
            @click="deleteTruck(scope.$index, scope.row)"
            icon="el-icon-delete"
            >删除</el-button
          >
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页组件 -->
    <Pagination
      v-bind:child-msg="pageparm"
      @callFather="callFather"
    ></Pagination>
    <!-- 编辑界面 -->
    <el-dialog
      :title="title"
      :visible.sync="editFormVisible"
      width="30%"
      @click="closeDialog"
    >
      <el-form label-width="120px" :model="editForm" ref="editForm">
        <el-form-item label="车辆类型">
          <el-select v-model="editForm.typeName" placeholder="请选择车辆类型">
            <el-option
              v-for="type in types"
              :key="type.id"
              :label="type.typeName"
              :value="type.typeName"
              :disabled="type.disabled"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="车牌号">
          <el-input
            size="small"
            v-model="editForm.truckMark"
            auto-complete="off"
            placeholder="请输入车牌号"
          ></el-input>
        </el-form-item>
        <el-form-item label="最大承受重量">
          <el-input-number
            v-model="editForm.maxWeight"
            :min="1"
            :max="100"
            label="最大承受重量"
          ></el-input-number
          ><span style="font-size: 20px">KG</span>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button size="small" @click="closeDialog">取消</el-button>
        <el-button
          size="small"
          type="primary"
          :loading="loading"
          class="title"
          @click="submitForm"
          >保存</el-button
        >
      </div>
    </el-dialog>
  </div>
</template>

<script>
import Axios from "axios";
import Pagination from "../../components/Pagination";
const baseUrl = "http://localhost:8888";
export default {
  data() {
    return {
      loading: false, //是显示加载
      editFormVisible: false, //控制编辑页面显示与隐藏
      title: "添加",
      editForm: {
        //添加表单数据
        typeName: "",
        truckMark: "",
        maxWeight: 1,
      },
      AllListData: [],
      currListData: [], //用户数据
      // 分页参数
      pageparm: {
        currentPage: 1,
        pageSize: 10,
        totalCount: 10,
        totalPage: 0,
      },
      //类型数组
      types: [],
    };
  },
  // 注册组件
  components: {
    Pagination,
  },
  /**
   * 创建完毕
   */
  created() {
    this.getdata();
  },
  methods: {
    //发车
    sendCar(index, row) {
      if (row.status === "运输中") {
        this.$confirm("当前车辆正在运输中.请勿重复发车", "发车", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        });
      } else {
        if (row.remainderWeight > 0) {
          this.$confirm(
            "当前车辆未装载满,还剩" +
              row.remainderWeight +
              "Kg可装载,确定要删除吗?",
            "发车",
            {
              confirmButtonText: "确定",
              cancelButtonText: "取消",
              type: "warning",
            }
          )
            .then(() => {
              this.sendCarAxios(row.id);
              //重新改变数据
              setTimeout(() => {
                this.getdata();
              }, 100);
            })
            .catch(() => {
              this.$message({
                type: "info",
                message: "已取消删除",
              });
            });
        } else {
          //直接发车
          this.sendCarAxios(row.id);
          setTimeout(() => {
            this.getdata();
          }, 100);
        }
      }
    },
    sendCarAxios(id) {
      //发送axios请求去后端
      Axios({
        method: "get",
        url: `${baseUrl}/truck/sendCar/` + id,
      });
    },
    //找出所有类型
    getTypes() {
      Axios({
        method: "get",
        url: `${baseUrl}/trucktype/list`,
      }).then((res) => {
        this.types = res.data.page.list;
      });
    },
    // 获取所有车辆信息
    getdata() {
      this.loading = true;
      Axios({
        method: "get",
        url: `${baseUrl}/truck/list`,
      }).then((res) => {
        this.loading = false;
        this.pageparm.totalCount = res.data.page.totalCount;
        this.pageparm.totalPage = res.data.page.totalPage;
        this.AllListData = res.data.page.list;
        //初始化分页
        this.setPaginations();
      });
    },
    setPaginations() {
      this.pageparm.currentPage = 1; //默认显示第一页
      this.pageparm.pageSize = 10; //默认大小10
      //显示数据
      this.currListData = this.AllListData.filter((item, index) => {
        return index < this.pageparm.pageSize;
      });
    },
    // 分页插件事件
    callFather(parm) {
      if (parm.pageSize) {
        //处理页面大小
        this.pageparm.currentPage = 1;
        this.pageparm.pageSize = parm.pageSize;
        this.currListData = this.AllListData.filter((item, index) => {
          return index < this.pageparm.pageSize;
        });
      }
      if (parm.currentPage) {
        //处理页码改变
        //获取当前页的第一个元素的索引
        let index = this.pageparm.pageSize * (parm.currentPage - 1);
        let currAllData = this.pageparm.pageSize * parm.currentPage; //当前所有数据总数
        let tablist = [];
        // let allData = this.AllTableData.list; //所有数据
        for (let i = index; i < currAllData; i++) {
          if (this.AllListData[i]) {
            tablist.push(this.AllListData[i]);
          }
        }
        this.currListData = tablist;
      }
    },
    // 搜索事件
    search() {
      // this.getdata(this.formInline);
    },
    //显示编辑界面(新增和修改一起)
    handleEdit: function (index, row) {
      this.editFormVisible = true;
      this.getTypes();
      this.title = "添加";
    },
    // 编辑、增加页面保存方法
    submitForm() {
      Axios({
        method: "post",
        url: `${baseUrl}/truck/save`,
        data: this.editForm,
      }).then((res) => {
        //关闭模态框
        this.editFormVisible = false;
        //重新获取数据
        this.getdata();
        //重置表当数据
        this.editForm.typeName = "";
        this.editForm.truckMark = "";
        this.editForm.maxWeight = 1;
      });
    },
    // 删除车辆信息
    deleteTruck(index, row) {
      this.$confirm("确定要删除吗?", "信息", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          Axios({
            method: "delete",
            url: `${baseUrl}/truck/delete/` + row.id,
          })
            .then((res) => {
              //更新数据
              this.getdata();
              if (res.data.code === 0) {
                this.$message({
                  type: "success",
                  message: "车辆已删除!",
                });
              } else {
                this.$message({
                  type: "info",
                  message: res.data.msg,
                });
              }
            })
            .catch((err) => {
              this.loading = false;
              this.$message.error("车辆删除失败，请稍后再试！");
            });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除",
          });
        });
    },
    // 关闭编辑、增加弹出框
    closeDialog() {
      this.editFormVisible = false;
    },
  },
};
</script>

<style scoped>
.user-search {
  margin-top: 20px;
}
.userRole {
  width: 100%;
}
</style>

 
 