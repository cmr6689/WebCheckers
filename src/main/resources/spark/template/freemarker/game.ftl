<!DOCTYPE html>
<head>
    <link rel="shortcut icon" href="/img/favicon.ico">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
    <title>${title} | Web Checkers</title>
    <link rel="stylesheet" href="/css/style.css">
    <link rel="stylesheet" href="/css/game.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
    <#assign currentUser = {"name":currentUser}>
    <#assign redPlayer = {"name":redPlayer}>
    <#assign whitePlayer = {"name":whitePlayer}>
    <script>
        window.gameData = {
            "gameID": ${gameID!'null'},
            "currentUser": "${currentUser.name}",
            "viewMode": "${viewMode}",
            "modeOptions": ${modeOptionsAsJSON!'{}'},
            "redPlayer": "${redPlayer.name}",
            "whitePlayer": "${whitePlayer.name}",
            "activeColor": "${activeColor}"
        };
    </script>
</head>
<body>
<div class="page">
    <h1>Web Checkers | Game View</h1>

    <#include "nav-bar2.ftl" />

    <div class="body" style="width:710px">

        <div id="help_text" class="INFO"></div>

        <div>
            <div id="game-controls">

                <fieldset id="game-info">
                    <legend>Info</legend>

                    <#include "message.ftl" />

                    <div>
                        <table data-color='RED'>
                            <tr>
                                <td><img src="../img/single-piece-red.svg"/></td>
                                <td class="name">Red</td>
                            </tr>
                        </table>
                        <table data-color='WHITE'>
                            <tr>
                                <td><img src="../img/single-piece-white.svg"/></td>
                                <td class="name">White</td>
                            </tr>
                        </table>
                    </div>
                </fieldset>

                <fieldset id="game-toolbar">
                    <legend>Controls</legend>
                    <div class="toolbar"></div>
                </fieldset>

            </div>
            <div id="columns" style="float:left; padding-right:6px">
                            <table>
                                    <tr><td style="color:blue; padding-top:34px; padding-bottom:34px">7</td></tr>
                                    <tr><td style="color:blue; padding-top:34px; padding-bottom:34px">6</td></tr>
                                    <tr><td style="color:blue; padding-top:34px; padding-bottom:34px">5</td></tr>
                                    <tr><td style="color:blue; padding-top:34px; padding-bottom:34px">4</td></tr>
                                    <tr><td style="color:blue; padding-top:34px; padding-bottom:34px">3</td></tr>
                                    <tr><td style="color:blue; padding-top:34px; padding-bottom:34px">2</td></tr>
                                    <tr><td style="color:blue; padding-top:34px; padding-bottom:34px">1</td></tr>
                                    <tr><td style="color:blue; padding-top:34px; padding-bottom:34px">0</td></tr>
                            </table>
            </div>
            <div class="game-board" style="float:right">
                <table id="game-board">
                    <#list board.iterator() as row>
                        <tr data-row="${row.index}">
                            <#list row.iterator() as space>
                                <td data-cell="${space.cellIdx}"
                                        <#if space.isValid() >
                                            class="Space"
                                        </#if>
                                >
                                    <#if space.piece??>
                                        <div class="Piece"
                                             id="piece-${row.index}-${space.cellIdx}"
                                             data-type="${space.piece.type}"
                                             data-color="${space.piece.color}">
                                        </div>
                                    </#if>
                                </td>
                            </#list>
                        </tr>
                    </#list>
                    </tbody>
                </table>
                </div>
            </div>
            <div id="rows">
                <table style="padding-top:6px; text-align:center; margin:auto; table-layout:fixed; width:680px">
                    <tr>
                        <td style="color:blue; padding-left:42.5px; padding-right:42.5px">0</td>
                        <td style="color:blue; padding-left:42.5px; padding-right:42.5px">1</td>
                        <td style="color:blue; padding-left:42.5px; padding-right:42.5px">2</td>
                        <td style="color:blue; padding-left:42.5px; padding-right:42.5px">3</td>
                        <td style="color:blue; padding-left:42.5px; padding-right:42.5px">4</td>
                        <td style="color:blue; padding-left:42.5px; padding-right:42.5px">5</td>
                        <td style="color:blue; padding-left:42.5px; padding-right:42.5px">6</td>
                        <td style="color:blue; padding-left:42.5px; padding-right:42.5px">7</td>
                    </tr>
                </table>
            </div>

        </div>
    </div>

    <audio id="audio" src="http://www.soundjay.com/button/beep-07.mp3" autostart="false"></audio>
    <audio id="champ" src="/img/champ.mp3" autostart="false"></audio>

    <script data-main="/js/game/index" src="/js/require.js"></script>

</body>
</html>
