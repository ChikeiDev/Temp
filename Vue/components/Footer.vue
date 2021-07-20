<template>
  <footer>
    <div class="footer-content">
      <div class="wrapper">
        <div class="copyright">
          <p>© 2021 CoolnameINC. <strong>All rights reserved</strong></p>
        </div>
        <div class="social-wrapper">
          <ul class="social">
            <li class="discord">
              <a href="#">
                <font-awesome-icon
                  :icon="['fab', 'discord']"
                  class="scaling-1"
                />
              </a>
            </li>
            <li class="twitter">
              <a href="#">
                <font-awesome-icon
                  :icon="['fab', 'twitter-square']"
                  class="scaling"
                />
              </a>
            </li>
            <li class="youtube">
              <a href="#">
                <font-awesome-icon
                  :icon="['fab', 'youtube-square']"
                  class="scaling"
                />
              </a>
            </li>
            <li class="reddit">
              <a href="#">
                <font-awesome-icon
                  :icon="['fab', 'reddit-square']"
                  class="scaling"
                />
              </a>
            </li>
          </ul>
        </div>
      </div>
    </div>
  </footer>
</template>

<style>
footer {
  position: absolute;
  width: 100%;
}

.social-wrapper {
  padding-top: 40px;
}

.social-wrapper ul.social {
  list-style: none;
  text-align: center;
  font-weight: 700;
  float: right;
}

.social-wrapper ul.social li {
  margin: 0 0 0 10px;
  float: left;
}

.social-wrapper ul.social li a {
  transition: all 0.3s ease 0s;
}

.social-wrapper ul.social li.discord a {
  color: #7280da;
}

.social-wrapper ul.social li.discord a:hover {
  color: #9ba5eb;
}

.social-wrapper ul.social li.twitter a {
  color: #08a0e9;
}

.social-wrapper ul.social li.twitter a:hover {
  color: #47baf0;
}

.social-wrapper ul.social li.youtube a {
  color: #ff0000;
}

.social-wrapper ul.social li.youtube a:hover {
  color: #ff4d4d;
}

.social-wrapper ul.social li.reddit a {
  color: #ff5700;
}

.social-wrapper ul.social li.reddit a:hover {
  color: #ff8a4f;
}

.social-wrapper .scaling {
  width: 32px;
  height: 32px;
}

.social-wrapper .scaling-1 {
  padding-top: 2px;
  width: 32px;
  height: 32px;
}

.footer-content {
  border-top: 1px solid #ffffff10;
  background: #060606;
  width: 100%;
  float: left;
}

.wrapper {
  width: 1200px;
  margin: 0 auto;
}

.footer-content .copyright {
  margin: 45px 0 0;
  width: 400px;
  height: 65px;
  float: left;
}

.footer-content .copyright p {
  color: #c4c4c4;
  font-weight: 600;
}

.footer-content .copyright p strong {
  color: #8686b4;
  font-weight: 400;
}
</style>