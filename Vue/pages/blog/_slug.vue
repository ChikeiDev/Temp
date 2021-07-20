<template>
  <article>
    <div class="page-wrapper">
      <div class="page-background" />
      <div class="header">
        <img
          :src="require(`~/assets/resources/${article.img}`)"
          alt=""
          class="img-design"
        />
      </div>

      <div class="article-content">
        <div class="article-header">
          <h1>{{ article.title }}</h1>
          <p>
            {{ article.section }} -
            <strong>{{ formatDate(article.createdAt) }}</strong>
          </p>
        </div>
        <nuxt-content :document="article" />
      </div>
    </div>
  </article>
</template>

<script>
export default {
  async asyncData({ $content, params }) {
    const article = await $content("blog", params.slug).fetch();
    return { article };
  },
  methods: {
    formatDate(date) {
      const options = { year: "numeric", month: "long", day: "numeric" };
      return new Date(date).toLocaleDateString("en", options);
    },
  },
};
</script>

<style>
.header img {
  width: 100%;
  border-radius: 5px;
}

.page-background {
  background: url("assets/resources/web-design/blog-page-background.png") bottom
    no-repeat;
  background-size: cover;
  height: 89vh;
  min-height: 600px;
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  z-index: -1;
}

.img-design {
  border: 0.5px solid #ffffff30;
}

article {
  display: block;
  margin: 0 auto;
  padding: 50px 30px;
  max-width: 100%;
}

.article-content {
  margin: auto;
  max-width: 900px;
  color: #a4a4a4;
}

.article-header {
  padding-top: 40px;
  padding-bottom: 40px;
}

.page-wrapper .article-header p strong {
  color: #c1c1c1;
}

.page-wrapper h1 {
  font-size: 32px;
}

.page-wrapper h2 {
  font-size: 28px;
}

.page-wrapper h3 {
  font-size: 24px;
}

.page-wrapper h4 {
  font-size: 19px;
}

.page-wrapper h5 {
  font-size: 15px;
}

.page-wrapper h1,
.page-wrapper h2,
.page-wrapper h3,
.page-wrapper h4,
.page-wrapper h5 {
  padding-bottom: 10px;
  color: #fff;
  font-weight: 700;
}

.page-wrapper ul {
  list-style: disc;
  padding: 10px 20px 20px;
  color: #d3cef0;
}

.page-wrapper p strong {
  color: #c2bde1;
}

.page-wrapper p em {
  color: #c2bde1;
}

.page-wrapper img {
  margin: auto;
  max-width: 100%;
  box-shadow: 0px 3px 8px 1px #000000;
  border: 1px solid rgba(255, 255, 255, 0.15);
  transition: all 0.3s ease 0s;
}

.page-wrapper a {
  color: #a185ee;
  text-decoration: underline;
  transition: all 0.3s ease 0s;
}

.page-wrapper a:hover {
  color: #ff99dc;
}

.page-wrapper img:hover {
  border: 1px solid rgba(175, 55, 218, 0.24);
}

.page-wrapper p {
  color: rgb(189, 189, 189);
  text-decoration: none;
  line-height: 1.5;
}
</style>