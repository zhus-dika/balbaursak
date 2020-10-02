import { Module } from 'vuex';

export const bucketStore: Module<any, any> = {
  state: {
    bucket: []
  },
  getters: {
    bucket: state => state.bucket
  },
  mutations: {
    setAllFromStorage(state) {
      const search = 'requestPoint';
      const values = Object.keys(localStorage)
        .filter( key => key.match(search) )
        .map( key => {
          const object = {
            key: key,
            val: parseInt(localStorage[key], 10)
          };
          return object;
        });
      state.bucket = values;
    },
    additem(state, item) {
      if (state.bucket.filter(ele => ele.key === item.key).length === 0) {
        state.bucket.push(item);
      } else {
        state.bucket = state.bucket.map(ele => {
          return (ele.key === item.key) ? item : ele;
        });
      }
    },
    removeitem(state, key) {
      state.bucket = state.bucket.filter(ele => ele.key !== key);
    },
    updateitem(state, item) {
      state.bucket = state.bucket.map(ele => {
        return (ele.key === item.key) ? item : ele;
      });
    },
  },
};
