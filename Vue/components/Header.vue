<template>
  <header>
    <nav class="nav">
      <div class="container centered">
        <ul class="navigation">
          <li class="content">
            <nuxt-link to="/" class="button home">Home</nuxt-link>
          </li>
          <li class="content">
            <nuxt-link to="/rules" class="button rules">Rules</nuxt-link>
          </li>
          <li class="content">
            <svg id="Rocket_color_" class="scaled" data-name="Rocket (color)" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" viewBox="0 0 305.97 649.87"><defs><style>.cls-1{fill:#e0e0e0;}.cls-2{fill:#15d1ad;}.cls-3{fill:#ff4200;}.cls-4{fill:#ff9d00;}.cls-5{fill:#ffe94a;}.cls-6{fill:#00ffda;}.cls-7{clip-path:url(#clip-path);}.cls-8{fill:#e9fffb;}.cls-9{fill:#e2e8e4;}.cls-10{fill:none;stroke:#515151;stroke-miterlimit:10;stroke-width:10px;}.cls-11{fill:#686868;}.cls-12{clip-path:url(#clip-path-2);}.cls-13{clip-path:url(#clip-path-3);}.cls-14{clip-path:url(#clip-path-4);}</style><clipPath id="clip-path"><path class="cls-1" d="M153,0s-193.41,161.54-56,500c0,0,11.34-10.57,56-10.57S209,500,209,500C346.39,161.55,153,0,153,0Z"/></clipPath><clipPath id="clip-path-2"><circle class="cls-2" cx="152.98" cy="424.79" r="26.48"/></clipPath><clipPath id="clip-path-3"><circle class="cls-2" cx="152.98" cy="332.39" r="34.05"/></clipPath><clipPath id="clip-path-4"><circle class="cls-2" cx="152.98" cy="218.39" r="43.13"/></clipPath></defs><title>Rocket color</title><g id="Flame"><path class="cls-3" d="M141.41,541.47c-17.78,8.45-44.27,34.26,11.57,108.38,55.84-74.12,29.36-99.93,11.58-108.38A27,27,0,0,0,141.41,541.47Z"/><path class="cls-4" d="M144.3,539.82C131.4,546,112.39,564.25,149,615.7a4.9,4.9,0,0,0,8,0c36.6-51.45,17.59-69.75,4.69-75.88A20.22,20.22,0,0,0,144.3,539.82Z"/><path class="cls-5" d="M146.59,538.3c-9.49,4.51-23.47,18,3.45,55.84a3.61,3.61,0,0,0,5.88,0c26.93-37.86,12.94-51.33,3.45-55.84A14.9,14.9,0,0,0,146.59,538.3Z"/></g><g id="Jet"><path id="Jet-2" data-name="Jet" class="cls-6" d="M153,484.49l-45.75,7.2s8.25,20.1,18.6,20.1c2.16,0,4.59.16,7.1.4a203.53,203.53,0,0,0,40.1,0c2.51-.24,4.94-.4,7.1-.4,10.35,0,18.6-20.1,18.6-20.1Z"/></g><g id="Wings"><path class="cls-6" d="M257.65,295.07l44.43,51s8.6,75,0,139.8l-64.84-68.43,5-112.66Z"/><path class="cls-6" d="M48.32,295.07,3.89,346s-8.6,75,0,139.8l64.85-68.43-5-112.66Z"/></g><g id="Base"><path class="cls-1" d="M153,0s-193.41,161.54-56,500c0,0,11.34-10.57,56-10.57S209,500,209,500C346.39,161.55,153,0,153,0Z"/><g class="cls-7"><polygon class="cls-6" points="66.28 127.04 184.48 129.74 245.98 127.04 226.48 35.84 164.98 -4.06 117.58 0.01 66.28 127.04"/><path class="cls-8" d="M100.21,127.19c9.27-50.55,25-96.37,50.37-129.95,0,0-19.86,32.65-30.34,131.45Z"/><path class="cls-9" d="M100.21,127.19c-30.66,167.11,9.65,386,9.65,386l19,3.86c-19.4-184.78-17.06-308.29-8.57-388.35Z"/><path class="cls-10" d="M69.08,126h0a863.9,863.9,0,0,0,167.8,0l.36,0"/></g></g><g id="Windows"><g id="Bottom_Window" data-name="Bottom Window"><circle class="cls-11" cx="152.98" cy="424.79" r="31.5"/><circle class="cls-2" cx="152.98" cy="424.79" r="26.48"/><g class="cls-12"><path class="cls-6" d="M179.46,403.7s-34,19.16-44.72,50.47l49.74,3.32Z"/></g></g><g id="Mid_Window" data-name="Mid Window"><circle class="cls-11" cx="152.98" cy="332.39" r="40.5"/><circle class="cls-2" cx="152.98" cy="332.39" r="34.05"/><g class="cls-13"><path class="cls-6" d="M187,305.27s-43.76,24.63-57.5,64.9l63.95,4.26Z"/></g></g><g id="Top_Window" data-name="Top Window"><circle class="cls-11" cx="152.98" cy="218.39" r="51.3"/><circle class="cls-2" cx="152.98" cy="218.39" r="43.13"/><g class="cls-14"><path class="cls-6" d="M196.11,184s-55.43,31.2-72.83,82.2l81,5.4Z"/></g></g></g></svg>
          </li>
          <li class="content">
            <nuxt-link to="/blogs" class="button blog">Blog</nuxt-link>
          </li>
          <li class="content">
            <nuxt-link to="/" class="button store">Store</nuxt-link>
          </li>
        </ul>
      </div>
    </nav>
  </header>
</template>

<script>
export default {
  tansition: "page",
};
</script>

<style>

li.content .scaled {
  width: 100px;
  height: 100px;
  color: rgb(255, 255, 255);
}

.nav {
  margin-top: 1.5rem;
  margin-bottom: 0.5rem;
}

.container {
  max-width: 1100px;
}

.centered {
  margin-left: auto;
  margin-right: auto;
}

.navigation {
  display: flex;
  justify-content: center;
  align-items: center;
  margin: 0 -0.75rem 0.75rem -0.75rem;
  letter-spacing: 0.1em;
  list-style: none;
  text-transform: uppercase;
  font-weight: 700;
  text-shadow: rgba(0, 0, 0, 0.3) 0 2px 0;
  font-size: 1.125rem;
}

.content {
  margin: 0 0.75rem 0 0.75rem;
}

.content a.button {
  display: flex;
  align-items: center;
  padding: 0.55rem 1.25rem 0.55rem 1.25rem;
  border: 1px solid hsla(0, 0%, 100%, 0.3);
  color: #fff;
  border-radius: 2px;
}

.content a.button.home {
  background-color: #ffbc49;
  border-bottom: 4px solid #bd8f42;
}

.content a.button.home:hover {

}

.content a.button.rules {
  background-color: #28c188;
  border-bottom: 4px solid #1e8e65;
}

.content a.button.blog {
  background-color: #37c4b4;
  border-bottom: 4px solid #228e82;
}

.content a.button.store {
  background-color: #f70174;
  border-bottom: 4px solid #95114e;
}
</style>