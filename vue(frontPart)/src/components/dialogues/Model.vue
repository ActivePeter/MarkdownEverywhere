<template>
  <div class="login">
    <el-dialog title="设置类别以及添加标签" :visible.sync="dialogVisible" width='300px'
      :close-on-click-modal='false' @close='onClose()' id="logindialog">
        
      
    </el-dialog>
  </div>
  

</template>

<script>
  import HorizonSpace from '@/components/common/HorizonSpace'
  import {Login, GetSms,Register} from "@/network/users";
  import {checkUsername, checkPassword, checkCode} from '@/utils/validate'

  export default {
    name: "login",
    components: {
        HorizonSpace,
    },
    
    methods:{
      
    },
    data(){
      return {
        dialogVisible:false
      };
    }
  }
</script>

<style scoped>
  .middle{
    margin:0 auto !important;
    text-align: center;
  }
  .btn{
    width: 100%;
  }
</style>
