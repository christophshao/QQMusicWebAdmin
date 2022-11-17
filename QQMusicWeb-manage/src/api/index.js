/*
*往后台传递相关的参数的js文件
*/

import {get,post} from "./http";

// 判断管理员是否登录成功（向后台的登录的代码传递参数）
export const getLoginStatus = (params) => post(`admin/login/status`,params);


/******歌手相关 转到后台的地址页面******/
// 查询歌手
export const getAllSinger = () => get(`singer/selectAllSinger`);

// 添加歌手
export const setSinger = (params) => post(`singer/addSinger`,params);

// 修改歌手
export const updateSinger = (params) => post(`singer/updateSinger`,params);

// 删除歌手
export const deleteSinger = (id) => get(`singer/deleteSinger?id=${id}`);


/*********歌曲相关 ******** */
// 根据歌手id查询相关歌曲
export const selectSongBySingerId = (id) => get(`song/singer/detail?singerId=${id}`);
// 修改歌曲
export const updateSong = (params) => post(`song/updateSong`,params);
// 删除歌曲
export const deleteSong = (id) => get(`song/deleteSong?id=${id}`);
// 查询所有歌曲
export const selectAllSong = () => get(`song/selectAllSong`);

// 根据歌曲id查询歌曲对象
export const songOfSongId = (id) => get(`song/detail?songId=${id}`);
//根据歌曲名获取歌曲对象
export const songOfSongName = (songName) => get(`song/songOfSongName?songName=${songName}`);


/******歌单相关 ************/
// 查询歌单
export const getAllSongList = () => get(`songList/selectAllSongList`);

// 添加歌单
export const setSongList = (params) => post(`songList/insertSongList`,params);

// 修改歌单
export const updateSongList = (params) => post(`songList/updateSongList`,params);

// 删除歌单
export const deleteSongList = (id) => get(`songList/deleteSongList?id=${id}`);


/******歌单的歌曲相关 转到后台的地址页面******/

//根据歌单id查询歌曲列表
export const listSongDetail = (songListId) => get(`listSong/detail?songListId=${songListId}`);

//给歌单增加歌曲
export const listSongAdd = (params) => post(`listSong/addListSong`,params);

// 删除一条歌曲,根据歌曲id和歌单id确定 要删除的这条歌曲
export const deleteListSong = (songId,songListId) => get(`listSong/deleteListSong?songId=${songId}&songListId=${songListId}`);








/******用户相关 转到后台的地址页面******/
// 查询用户
export const getAllConsumer = () => get(`consumer/selectAllConsumer`);

// 添加用户
export const setConsumer = (params) => post(`consumer/addConsumer`,params);

// 修改用户
export const updateConsumer = (params) => post(`consumer/updateConsumer`,params);

// 删除用户
export const deleteConsumer = (id) => get(`consumer/deleteConsumer?id=${id}`);


