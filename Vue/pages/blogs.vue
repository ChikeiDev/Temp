<template>
<div class="page-wrapper">
    <div class="blog-page">
      <h2>All blog posts</h2>
      <div class="articles">
        <div class="article" v-for="article of articles" :key="article">
          <nuxt-link :to="{ name: 'blog-slug', params: { slug: article.slug } }">
            <div class="article-inner">
              <img :src="require(`~/assets/resources/${article.img}`)" alt="" />
              <div class="detail">
                <h3>{{ article.title }}</h3>
                <p>{{ article.description }}</p>
              </div>
            </div>
          </nuxt-link>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  async asyncData({ $content, params }) {
    const articles = await $content("blog", params.slug)
      .only(["title", "description", "img", "slug"])
      .sortBy("createdAt", "desc")
      .fetch();

    return { articles };
  },
};
</script>

<style>
.home-page {
  padding: 50px 30px;
}
.articles {
  margin: 0 auto;
  max-width: 800px;
}
.article {
  margin-bottom: 15px;
}
.article-inner {
  padding: 15px;
  background: #fff;
  box-shadow: 0px 3px 6px rgba(0, 0, 0, 0.1);
  border-radius: 8px;
  display: flex;
}
.article-inner img {
  display: block;
  width: 100%;
  max-width: 300px;
}
.article-inner .detail {
  padding-left: 15px;
  padding-right: 15px;
}
</style>