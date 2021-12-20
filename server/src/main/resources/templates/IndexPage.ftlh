<#import "./common.ftlh" as c>

<@c.page>
    <section>
        <div>
            <h2>Project by:</h2>
            <ul class="check-list">
                <li><a class="link" href="https://github.com/RoyalTunez">Denis Tribrat</a> - engine</li>
                <li><a class="link" href="https://github.com/YuriNeverov">Yuri Neverov</a> - testing and management</li>
                <li><a class="link" href="https://github.com/Mr-Ravil">Ravil Galiev</a> - server</li>
                <li><a class="link" href="https://github.com/GaDiss">Sergey Pankratov</a> - frontend</li>
                <li><a class="link" href="">Anna Andreeva</a> - drew a logo</li>
            </ul>
        </div>
    </section>

    <div id="myBoard" style="width: 400px"></div>
    <script>
        var board = null
        var game = new Chess()
        var $status = $('#status')
        var $fen = $('#fen')
        var $pgn = $('#pgn')

        var config = {
            draggable: true,
            position: 'start',
            onDragStart: onDragStart,
            onDrop: onDrop,
            onSnapEnd: onSnapEnd
        }
        board = Chessboard('myBoard', config)

        updateStatus()
    </script>

</@c.page>