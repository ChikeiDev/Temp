<template>
  <div class="page-wrapper">
    <div class="home-page">
      <!-- <h2>Latest posts</h2>
      <div class="articles">
        <div class="article" v-for="article of articles" :key="article">
          <nuxt-link :to="{ name: 'blog-slug', params: { slug: article.slug } }">
            <div class="article-inner">
              <img :src="require(`~/assets/resources/${article.img}`)" alt="" />
              <div class="detail">
                <h3>{{ article.title }}</h3>
                <p>{{ article.description }}</p>
                <div class="meta">
                  <p>{{ article.date }}</p>
                </div>
              </div>
            </div>
          </nuxt-link>
        </div>
      </div> -->
      <section id="social">
        <div class="container">
          <div class="content-grid">
            <div class="content-right">
              <div class="social-content">
                <div class="content-header">
                  <h2>Interact with the community!</h2>
                </div>
                <p>
                  We believe that everyone should have a voice in a community, and that's why we've created a Discord for the server!
                  Join in our our discord to communicate with both the staff and other players, and help out coming up with new wonderful
                  and amazing ideas for future updates towards the server!
                </p>
                <a class="join-button stylize" href="#">Enter our Discord</a>
              </div>
            </div>
            <div class="content-left">
              <div class="social-image">
                <img class="image-top" src="~/assets/resources/about-player.png">
                <img class="image-bottom" src="~/assets/resources/about-phone.png">
              </div>
            </div>
          </div>
        </div>
      </section>
    </div>
  </div>
</template>

<script>
export default {
  async asyncData({ $content, params }) {
    const articles = await $content("blog", params.slug)
      .only(["title", "description", "img", "slug", "date", "author"])
      .sortBy("createdAt", "asc")
      .limit(3)
      .fetch();

    return { articles };
  },
};
</script>

<style>
.home-page {
  padding: 50px 30px;
}
img {
  max-width: 100%;
  height: auto;
  vertical-align: middle;
  border-style: none;
}
.container {
  width: 100%;
  margin-left: auto;
  margin-right: auto;
  max-width: 1100px;
}
.content-grid {
  align-items: center;
  justify-content: center;
  display: flex;
  margin-right: -15px;
  margin-left: -15px;
}

.content-right {
  position: relative;
  width: 100%;
  padding-right: 15px;
  padding-left: 15px;
  flex: 0 0 66.666667%;
  max-width: 66.666667%;
}

.content-header {
  margin-bottom: 15px;
  position: relative;
  margin-top: -8px;
  font-size: 20px;
  text-transform: uppercase;
}

.content-header h2 {
  color: #fff;
  font-weight: 600;
  margin: 0;
  line-height: 1.3;
}

.content-right p {
  color: rgb(241, 220, 255);
  line-height: 1.5;
  margin-bottom: 15px;
}

.content-right .join-button {
  align-items: center;
  padding: 0.55rem 1.25rem 0.55rem 1.25rem;
  border: 1px solid hsla(0, 0%, 100%, 0.3);
  color: #fff;
  border-radius: 1px;
}

.content-right .stylize {
  background-color: #7289DA;
  border-bottom: 4px solid #5062a2;
  margin-top: 30px;
  text-transform: uppercase;
  font-weight: 600;
}

.content-right .resize {
  margin-top: 3rem;
}

.content-left {
  position: relative;
  width: 100%;
  padding-right: 15px;
  padding-left: 15px;
  margin-top: 0 !important;
  flex: 0 0 33.333333%;
  max-width: 33.333333%;
}

.social-image {
  position: inherit;
}

.social-image .image-top {
  position: relative;
  z-index: 1;
  margin-bottom: 25px;
  animation: bounce 3s linear infinite;
}

.social-image .image-bottom {
  position: absolute;
  bottom: 0;
  left: 0;
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
  position: relative;
}
.article-inner .detail .meta {
  position: absolute;
  bottom: 0;
}
.article-inner .detail .meta p {
  color: rgb(97, 0, 0);
}

@keyframes bounce {
  0% {
    transform: translateY(0);
  } 25% {
    transform: translateY(-10px);
  } 50% {
    transform: translateY(-20px);
  } 75% {
    transform: translateY(-10px);
  } 100% {
    transform: translateY(0);
  }
}
</style>
