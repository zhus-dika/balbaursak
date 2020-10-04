<template>
    <div id="footer" class="footer">
        <p class="footer-text" v-text="$t('footer')">This is your footer</p>
    </div>
</template>

<script lang="ts" src="./jhi-footer.component.ts">
</script>

<style scoped>
    .footer {
        margin-top: 10px;
        text-align: left;
        position: absolute;
        bottom: -51px;
        left: 0;
        height: 51px;
        width: 100%;
        background-color: black;
        color: white;
        z-index: 10;
    }
    .footer-text {
        margin-left: 10px;
        margin-top: 10px;
    }
</style>
