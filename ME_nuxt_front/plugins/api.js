export default ({ app: { $request } }, inject) => {
  inject('api', {
    // 首页数据
    /**
     * 轮播图数据
     */
    getArticle(id) {
      return $request.get('article?id=' + id)
    },
    getList(id) {
      return $request.get('list')
    },
    addCommentRoot(name1, content1, contact1, email1) {
      const data = new FormData()
      data.append('name', name1)
      data.append('contact', contact1)
      data.append('content', content1)
      data.append('email', email1)
      data.append('fatherID', -1)
      return $request.post(
        'addComment',
        data
        // {
        //   name: name1,
        //   content: content1,
        //   contact: contact1,
        //   email: email1,
        //   fatherID: -1,
        // }
      )
    },
    getCommentsPageLatest() {
      return $request.get(
        'getCommentsPageLatest'
        // data
        // {
        //   name: name1,
        //   content: content1,
        //   contact: contact1,
        //   email: email1,
        //   fatherID: -1,
        // }
      )
    },
    getCommentsPageNext(id) {
      return $request.get('getCommentsNextPage?id=' + id)
    },
  })
}
