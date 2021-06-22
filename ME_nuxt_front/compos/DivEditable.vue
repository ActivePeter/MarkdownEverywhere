<template>
  <div
    class="div-editable"
    contenteditable="true"
    v-html="textIn"
    @input="changeText"
    @focus="isChange = false"
    @blur="blurFunc"
  ></div>
</template>

<script>
export default {
  name: 'DivEditable',
  // props: {
  //   value: {
  //     type: String,
  //     default: '',
  //   },
  // },
  data() {
    return {
      textIn: this.value,
      value: '',
      isChange: true,
    }
  },
  watch: {
    value() {
      if (this.isChange) {
        this.textIn = this.value
      }
    },
  },
  methods: {
    changeText() {
      this.value = this.$el.innerHTML
      this.$emit('input', this.$el.innerHTML)
    },
    blurFunc() {
      this.isChange = true
      // this.$emit('blurFunc')
    },
    clear() {
      this.textIn = ''
      this.value = ''
      this.$el.innerHTML = ''
      console.log('clear')
    },
  },
}
</script>

<style>
.div-editable {
  margin-top: 15px;
  margin-bottom: 10px;
  text-indent: 14px;
  line-height: 25px;
  width: 100%;
  height: 420px;
  outline: 0;
  height: max-content;
  font-size: 18px;
  color: rgba(255, 255, 255, 0.623);
}
</style>
