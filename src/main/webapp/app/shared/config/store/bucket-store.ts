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
            val: localStorage[key]
          };
          return object;
        });
      state.bucket = values;
    },
    additem(state, item) {
      state.bucket.push(item);
    },
    removeitem(state, item) {
      state.bucket = state.bucket.filter(ele => ele.produceId !== item.produceId);
    },
    updateitem(state, item) {
      state.bucket = state.bucket.map(ele => {
        return (ele.produceId === item.produceId) ? item : ele;
      });
    },
  },
};
